from http.client import HTTPConnection
from io import BytesIO
from socket import gaierror
from unittest import mock

from django.contrib.auth.models import User, Permission
from django.test import TestCase
from django.urls import reverse
from django.utils.translation import gettext_lazy as _, ngettext_lazy as __

from x360.models import GameModel


class TestDownloadGamesFromXbox(TestCase):
    def setUp(self):
        self.well_formatted_response = b"""
            Irrelevant content before
            var bcgames = [
                {
                    image: 'http://www.game.com/cover1.jpg',
                    url: 'http://www.game.com/store/game1/',
                    title: 'Game 1',
                    justreleased: 0,
                    fanfav: 0,
                    includedLoc: "not supported yet",
                    excludedLoc: "not supported yet"
                },
                {
                    image: 'http://www.game.com/cover2.jpg',
                    url: 'http://www.game.com/store/game2/',
                    title: 'Game 2',
                    justreleased: 0,
                    fanfav: 0,
                    includedLoc: "not supported yet",
                    excludedLoc: "not supported yet"
                }
            ]
            Irrelevant content after
            """
        self.no_white_spaces_response = b"""Irrelevantv content before var bcgames=[{image:'http://www.game.com/cover1.jpg',url:'http://www.game.com/store/game1/',title:'Game 1',justreleased:0,fanfav:0,includedLoc:"not supported yet",excludedLoc:"not supported yet"},{image:'http://www.game.com/cover2.jpg',url:'http://www.game.com/store/game2/',title:'Game 2',justreleased:0,fanfav:0,includedLoc:"not supported yet",excludedLoc:"not supported yet"}]; Irrelevant content after"""
        self.well_formatted_response_with_couple_json_lists = b"""
                    Irrelevant content before
                    var list1 = [{
                        key: value,
                    }];
                    var bcgames = [
                        {
                            image: 'http://www.game.com/cover1.jpg',
                            url: 'http://www.game.com/store/game1/',
                            title: 'Game 1',
                            justreleased: 0,
                            fanfav: 0,
                            includedLoc: "not supported yet",
                            excludedLoc: "not supported yet"
                        },
                        {
                            image: 'http://www.game.com/cover2.jpg',
                            url: 'http://www.game.com/store/game2/',
                            title: 'Game 2',
                            justreleased: 0,
                            fanfav: 0,
                            includedLoc: "not supported yet",
                            excludedLoc: "not supported yet"
                        }
                    ]
                    var list2 = [{
                        key: value,
                    }];
                    Irrelevant content after
                    """
        self.no_white_spaces_response_with_couple_json_lists = b"""Irrelevantv content before var list1=[key:value,}];var bcgames=[{image:'http://www.game.com/cover1.jpg',url:'http://www.game.com/store/game1/',title:'Game 1',justreleased:0,fanfav:0,includedLoc:"not supported yet",excludedLoc:"not supported yet"},{image:'http://www.game.com/cover2.jpg',url:'http://www.game.com/store/game2/',title:'Game 2',justreleased:0,fanfav:0,includedLoc:"not supported yet",excludedLoc:"not supported yet"}];var list2=[key:value,}];Irrelevant content after"""
        self.empty_response = b''
        self.games = [
            dict(title='Game 1', cover_link='http://www.game.com/cover1.jpg',
                 store_link='http://www.game.com/store/game1/'),
            dict(title='Game 2', cover_link='http://www.game.com/cover2.jpg',
                 store_link='http://www.game.com/store/game2/'),
        ]
        administrator = User.objects.create_superuser('admin', 'admin@example.com', 'password')
        administrator.set_password('password')
        administrator.save()
        user = User.objects.create_user('user', 'user@example.com', 'password')
        user.set_password('password')
        user.user_permissions.add(Permission.objects.get(name='Can change {0}'.format(GameModel._meta.verbose_name)))
        user.save()
        self.info = GameModel._meta.app_label, GameModel._meta.model_name

    def tearDown(self):
        self.client.logout()

    def login_as_administrator(self):
        self.client.login(username='admin', password='password')

    def login_as_user_with_no_add_permission(self):
        self.client.login(username='user', password='password')

    @mock.patch.object(HTTPConnection, 'request')
    @mock.patch.object(HTTPConnection, 'getresponse')
    def test_well_formatted_response(self, mock_response, mock_request):
        """All games from server response must be added to database and user musts see proper message"""
        mock_request.return_value = None
        mock_response.return_value = BytesIO(self.well_formatted_response)
        self.login_as_administrator()
        response = self.client.get(reverse('admin:%s_%s_download_from_xbox' % self.info), follow=True)
        self.assertEqual(len(self.games), GameModel.objects.count(), 'Incorrect number of games added')
        self.check_if_games_added_correctly(self.games)
        self.check_if_response_contains_message(response, self.successfully_added_games_message(len(self.games)))

    @mock.patch.object(HTTPConnection, 'request')
    @mock.patch.object(HTTPConnection, 'getresponse')
    def test_no_white_spaces_response(self, mock_response, mock_request):
        """Response with proper data, but without white spaces should be treated as normal"""
        mock_request.return_value = None
        mock_response.return_value = BytesIO(self.no_white_spaces_response)
        self.login_as_administrator()
        response = self.client.get(reverse('admin:%s_%s_download_from_xbox' % self.info), follow=True)
        self.assertEqual(len(self.games), GameModel.objects.count(), 'Incorrect number of games added')
        self.check_if_games_added_correctly(self.games)
        self.check_if_response_contains_message(response, self.successfully_added_games_message(len(self.games)))

    @mock.patch.object(HTTPConnection, 'request')
    @mock.patch.object(HTTPConnection, 'getresponse')
    def test_well_formatted_response_with_couple_json_lists(self, mock_response, mock_request):
        """Response must be parsed to select list with games"""
        mock_request.return_value = None
        mock_response.return_value = BytesIO(self.well_formatted_response_with_couple_json_lists)
        self.login_as_administrator()
        response = self.client.get(reverse('admin:%s_%s_download_from_xbox' % self.info), follow=True)
        self.assertEqual(len(self.games), GameModel.objects.count(), 'Incorrect number of games added')
        self.check_if_games_added_correctly(self.games)
        self.check_if_response_contains_message(response, self.successfully_added_games_message(len(self.games)))

    @mock.patch.object(HTTPConnection, 'request')
    @mock.patch.object(HTTPConnection, 'getresponse')
    def test_no_white_spaces_response_with_couple_json_lists(self, mock_response, mock_request):
        """Response must be parsed to select list with games even if there are no white spaces in response"""
        mock_request.return_value = None
        mock_response.return_value = BytesIO(self.no_white_spaces_response_with_couple_json_lists)
        self.login_as_administrator()
        response = self.client.get(reverse('admin:%s_%s_download_from_xbox' % self.info), follow=True)
        self.assertEqual(len(self.games), GameModel.objects.count(), 'Incorrect number of games added')
        self.check_if_games_added_correctly(self.games)
        self.check_if_response_contains_message(response, self.successfully_added_games_message(len(self.games)))

    @mock.patch.object(HTTPConnection, 'request')
    @mock.patch.object(HTTPConnection, 'getresponse')
    def test_empty_response(self, mock_response, mock_request):
        """Empty response from server musts cause error message and can't add any game"""
        mock_request.return_value = None
        mock_response.return_value = BytesIO(self.empty_response)
        self.login_as_administrator()
        response = self.client.get(reverse('admin:%s_%s_download_from_xbox' % self.info), follow=True)
        self.assertEqual(0, GameModel.objects.count(), 'Added games without permissions')
        self.check_if_response_contains_message(response, self.problem_with_downloaded_data_message())

    @mock.patch.object(HTTPConnection, 'request')
    @mock.patch.object(HTTPConnection, 'getresponse')
    def test_response_with_already_added_game(self, mock_response, mock_request):
        """Only new games must be added to database and user need to see proper message"""
        mock_request.return_value = None
        mock_response.return_value = BytesIO(self.well_formatted_response)
        self.login_as_administrator()
        GameModel.objects.create(**self.games[0])
        response = self.client.get(reverse('admin:%s_%s_download_from_xbox' % self.info), follow=True)
        self.assertEqual(len(self.games), GameModel.objects.count(), 'Incorrect number of games added')
        self.check_if_games_added_correctly(self.games[1:])
        self.check_if_response_contains_message(response, self.successfully_added_games_message(len(self.games[1:])))

    @mock.patch.object(HTTPConnection, 'request')
    @mock.patch.object(HTTPConnection, 'getresponse')
    def test_response_with_no_new_games(self, mock_response, mock_request):
        """No games can be added if all games already exists in database and user musts to see proper message"""
        mock_request.return_value = None
        mock_response.return_value = BytesIO(self.well_formatted_response)
        self.login_as_administrator()
        for game in self.games:
            GameModel.objects.create(**game)
        response = self.client.get(reverse('admin:%s_%s_download_from_xbox' % self.info), follow=True)
        self.assertEqual(len(self.games), GameModel.objects.count(), 'Incorrect number of games added')
        self.check_if_response_contains_message(response, self.successfully_added_games_message(0))

    @mock.patch.object(HTTPConnection, 'request')
    @mock.patch.object(HTTPConnection, 'getresponse')
    def test_make_download_request_without_permissions(self, mock_response, mock_request):
        """User without permission can't perform download action and musts to see proper error message"""
        mock_request.return_value = None
        mock_response.return_value = BytesIO(self.well_formatted_response)
        self.login_as_user_with_no_add_permission()
        response = self.client.get(reverse('admin:%s_%s_download_from_xbox' % self.info), follow=True)
        self.assertEqual(0, GameModel.objects.count(), 'Added games without permissions')
        self.check_if_response_contains_message(response, self.user_has_no_permission_message())

    @mock.patch.object(HTTPConnection, 'request')
    @mock.patch.object(HTTPConnection, 'getresponse')
    def test_no_connection_to_external_server(self, mock_response, mock_request):
        """No connection with external server musts be shown as error message to user"""
        mock_request.side_effect = gaierror()
        self.login_as_administrator()
        response = self.client.get(reverse('admin:%s_%s_download_from_xbox' % self.info), follow=True)
        self.check_if_response_contains_message(response, self.problem_connecting_to_server_message())

    def test_user_with_permission_sees_download_button(self):
        """User with permission musts see download button"""
        self.login_as_administrator()
        response = self.client.get(reverse('admin:%s_%s_changelist' % self.info), follow=True)
        self.assertContains(response, _('Download from Xbox.com'), 1, 200, 'User does not see the download button')

    def test_user_without_permission_does_not_see_download_button(self):
        """User without permission can't see the download button"""
        self.login_as_user_with_no_add_permission()
        response = self.client.get(reverse('admin:%s_%s_changelist' % self.info), follow=True)
        self.assertNotContains(response, _('Download from Xbox.com'), 200, 'User sees the download button')

    def check_if_games_added_correctly(self, games):
        error_message = 'Inconsistently added game'
        try:
            for game in games:
                game_from_title = GameModel.objects.get(title=game['title'])
                game_from_cover_link = GameModel.objects.get(cover_link=game['cover_link'])
                game_from_store_link = GameModel.objects.get(store_link=game['store_link'])
                self.assertEqual(game_from_title, game_from_store_link, error_message)
                self.assertEqual(game_from_title, game_from_cover_link, error_message)
        except GameModel.DoesNotExist:
            self.fail('Data was not added correctly')

    def check_if_response_contains_message(self, response, required_message):
        response_messages = []
        for message in response.context['messages']:
            response_messages.append(str(message))
        self.assertIn(required_message, response_messages, 'Response does not contain required message')

    @staticmethod
    def successfully_added_games_message(count):
        return __('Successfully added %(count)d %(name)s',
                  'Successfully added %(count)d %(plural_name)s',
                  count) % dict(
            count=count,
            name=GameModel._meta.verbose_name,
            plural_name=GameModel._meta.verbose_name_plural
        )

    @staticmethod
    def problem_with_downloaded_data_message():
        return _('There was problem with downloaded data')

    @staticmethod
    def problem_connecting_to_server_message():
        return _('Encountered problem connection external server')

    @staticmethod
    def user_has_no_permission_message():
        return _('You do not have permission to perform this action')
