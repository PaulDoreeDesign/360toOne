from random import randint

from django.contrib.auth.models import User, Permission
from django.test import TestCase
from django.urls import reverse

from x360.models import GameModel


class TestPublishActions(TestCase):
    def setUp(self):
        administrator = User.objects.create_superuser('admin', 'admin@example.com', 'password')
        administrator.set_password('password')
        administrator.save()
        user = User.objects.create_user('user', 'user@example.com', 'password')
        user.set_password('password')
        user.user_permissions.add(Permission.objects.get(name='Can add {0}'.format(GameModel._meta.verbose_name)))
        user.save()
        self.games = self.create_games()
        self.info = GameModel._meta.app_label, GameModel._meta.model_name

    def create_games(self):
        games = []
        for i in range(1, 10):
            title = 'Game{0}'.format(i)
            cover_link = 'http://www.game.com/cover{0}.jpg'.format(i)
            store_link = 'http://www.game.com/store/game{0}/'.format(i)
            games.append(GameModel.objects.create(title=title, cover_link=cover_link, store_link=store_link))
        return games

    def tearDown(self):
        self.client.logout()

    def login_as_administrator(self):
        self.client.login(username='admin', password='password')

    def login_as_user_with_no_change_permission(self):
        self.client.login(username='user', password='password')

    def test_publish_without_change_permission(self):
        self.login_as_user_with_no_change_permission()
        game_to_publish_pk = self.games[randint(0, len(self.games) - 1)].pk
        data = {'action': 'mark_games_as_published', '_selected_action': [game_to_publish_pk]}
        self.client.post(reverse('admin:%s_%s_changelist' % self.info), data)
        for game in GameModel.objects.all():
            self.assertFalse(game.published, 'Unselected game was published')

    def test_unpublish_without_change_permission(self):
        self.publish_all_games()
        self.login_as_user_with_no_change_permission()
        game_to_unpublish_pk = self.games[randint(0, len(self.games) - 1)].pk
        data = {'action': 'mark_games_as_unpublished', '_selected_action': [game_to_unpublish_pk]}
        self.client.post(reverse('admin:%s_%s_changelist' % self.info), data)
        for game in GameModel.objects.all():
            self.assertTrue(game.published, 'Unselected game was unpublished')

    def test_publish_one_game(self):
        self.login_as_administrator()
        game_to_publish_pk = self.games[randint(0, len(self.games) - 1)].pk
        data = {'action': 'mark_games_as_published', '_selected_action': [game_to_publish_pk]}
        self.client.post(reverse('admin:%s_%s_changelist' % self.info), data)
        self.assertTrue(GameModel.objects.get(pk=game_to_publish_pk).published, 'Game was not published')
        unpublished_games = GameModel.objects.exclude(pk=game_to_publish_pk)
        for game in unpublished_games:
            self.assertFalse(game.published, 'Unselected game was published')

    def test_publish_many_games(self):
        self.login_as_administrator()
        start = randint(0, (len(self.games) - 1) / 2)
        stop = randint(start + 1, len(self.games) - 1)
        games_to_publish_pks = [game.pk for game in self.games[start:stop]]
        data = {'action': 'mark_games_as_published', '_selected_action': games_to_publish_pks}
        self.client.post(reverse('admin:%s_%s_changelist' % self.info), data)
        published_games = GameModel.objects.filter(pk__in=games_to_publish_pks)
        unpublished_games = GameModel.objects.exclude(pk__in=games_to_publish_pks)
        for game in published_games:
            self.assertTrue(game.published, 'Game was not published')
        for game in unpublished_games:
            self.assertFalse(game.published, 'Unselected game was published')

    def test_unpublish_one_game(self):
        self.publish_all_games()
        self.login_as_administrator()
        game_to_unpublish_pk = self.games[randint(0, len(self.games) - 1)].pk
        data = {'action': 'mark_games_as_unpublished', '_selected_action': [game_to_unpublish_pk]}
        self.client.post(reverse('admin:%s_%s_changelist' % self.info), data)
        self.assertFalse(GameModel.objects.get(pk=game_to_unpublish_pk).published, 'Game was not unpublished')
        published_games = GameModel.objects.exclude(pk=game_to_unpublish_pk)
        for game in published_games:
            self.assertTrue(game.published, 'Unselected game was unpublished')

    def test_unpublish_many_games(self):
        self.publish_all_games()
        self.login_as_administrator()
        start = randint(0, (len(self.games) - 1) / 2)
        stop = randint(start + 1, len(self.games) - 1)
        games_to_unpublish_pks = [game.pk for game in self.games[start:stop]]
        data = {'action': 'mark_games_as_unpublished', '_selected_action': games_to_unpublish_pks}
        self.client.post(reverse('admin:%s_%s_changelist' % self.info), data)
        unpublished_games = GameModel.objects.filter(pk__in=games_to_unpublish_pks)
        published_games = GameModel.objects.exclude(pk__in=games_to_unpublish_pks)
        for game in unpublished_games:
            self.assertFalse(game.published, 'Game was not published')
        for game in published_games:
            self.assertTrue(game.published, 'Unselected game was published')

    def publish_all_games(self):
        GameModel.objects.all().update(published=True)
