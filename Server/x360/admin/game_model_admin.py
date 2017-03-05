import json
import re
from http.client import HTTPConnection
from socket import gaierror

from django.conf.urls import url
from django.contrib import admin, messages
from django.contrib.admin import register
from django.core.exceptions import ValidationError
from django.shortcuts import HttpResponseRedirect
from django.urls import reverse
from django.utils.translation import gettext_lazy as _, ngettext_lazy as __

from x360.models import GameModel, RegionModel


@register(GameModel)
class GameModelAdmin(admin.ModelAdmin):
    actions = ['mark_games_as_published', 'mark_games_as_unpublished']

    def download_games_list_from_xbox(self, request):
        if self.has_add_permission(request):
            try:
                added_games = self.add_games_from_xbox(request)
                self.message_user(request,
                                  __('Successfully added %(count)d %(name)s',
                                     'Successfully added %(count)d %(plural_name)s',
                                     added_games) % dict(
                                      count=added_games,
                                      name=GameModel._meta.verbose_name,
                                      plural_name=GameModel._meta.verbose_name_plural
                                  ),
                                  level=messages.INFO)
            except (ValueError, AttributeError) as e:
                print(e)
                self.message_user(request, _('There was problem with downloaded data'), level=messages.ERROR)
            except gaierror:
                self.message_user(request, _('Encountered problem connection external server'), level=messages.ERROR)
        else:
            self.message_user(request, _('You do not have permission to perform this action'), level=messages.ERROR)
        info = self.model._meta.app_label, self.model._meta.model_name
        return HttpResponseRedirect(reverse('admin:%s_%s_changelist' % info))

    def add_games_from_xbox(self, request):
        games_from_xbox = self.parse_games_list_from_internet()
        added_games = 0
        for game in games_from_xbox:
            try:
                game_dict = dict(title=game['title'].lstrip().rstrip(), cover_link=game['cover_link'], store_link=game['store_link'])
                game_object = GameModel.objects.create(**game_dict)
                available_regions_codes = [code.lstrip().rstrip().lower() for code in
                                           game['available_regions'].lstrip().rstrip().split(',') if code != '']
                available_regions = RegionModel.objects.filter(code__in=available_regions_codes)
                for region in available_regions:
                    game_object.available_regions.add(region)
                excluded_regions_codes = [code.lstrip().rstrip().lower() for code in
                                          game['excluded_regions'].lstrip().rstrip().split(',') if code != '']
                excluded_regions = RegionModel.objects.filter(code__in=excluded_regions_codes)
                for region in excluded_regions:
                    game_object.excluded_regions.add(region)
            except ValidationError:  # Validation error is raised if Game is already in database
                pass
            else:
                added_games += 1
        return added_games

    def parse_games_list_from_internet(self):
        site_content_string = self.download_site_string()
        json_string = re.search('(var bcgames)(\s*=\s*)(?P<content>\[[\S\s]*?\])', site_content_string).group('content')
        json_string = self.prepare_json_string(json_string)
        json_string = self.remove_unused_fields(json_string)
        json_string = self.fix_malformed_json(json_string)
        return json.loads(json_string)

    def prepare_json_string(self, json_string):
        json_string = re.sub('title(:.*?(?P<start>[\'"])(?P<name>.*?)(?P=start)[.\s]*?(?P<after>[,}\]]))',
                             '"title":"\g<name>"\g<after>',
                             json_string)
        json_string = re.sub('image(:.*?(?P<start>[\'"])(?P<name>.*?)(?P=start)[.\s]*?(?P<after>[,}\]]))',
                             '"cover_link":"\g<name>"\g<after>',
                             json_string)
        json_string = re.sub('url(:.*?(?P<start>[\'"])(?P<name>.*?)(?P=start)[.\s]*?(?P<after>[,}\]]))',
                             '"store_link":"\g<name>"\g<after>',
                             json_string)
        json_string = re.sub('includedLoc:(.*?(?P<start>[\'"])(?P<value>.*?)(?P=start)[.\s]*?)(?P<after>[,}\]])',
                             '"available_regions":"\g<value>"\g<after>', json_string)
        json_string = re.sub('excludedLoc:(.*?(?P<start>[\'"])(?P<value>.*?)(?P=start)[.\s]*?)(?P<after>[,}\]])',
                             '"excluded_regions":"\g<value>"\g<after>', json_string)
        return json_string

    def remove_unused_fields(self, json_string):
        json_string = re.sub('((justreleased)|(fanfav)).*?(?P<after>[,}\]])', '\g<after>', json_string)
        return json_string

    def fix_malformed_json(self, json_string):
        json_string = re.sub(',[,\s]*,+', ',', json_string)
        json_string = re.sub('(?P<start>[{\[])[,\s]+', '\g<start>', json_string)
        json_string = re.sub('[,\s]+(?P<end>[}\]])', '\g<end>', json_string)
        return json_string

    def download_site_string(self):
        xbox_site_url = 'www.xbox.com'
        games_list_url = '/pl-PL/xbox-one/backward-compatibility/available-games'
        connection = HTTPConnection(xbox_site_url)
        connection.request('GET', games_list_url)
        return connection.getresponse().read().decode()

    def mark_games_as_published(self, request, queryset):
        queryset.update(published=True)
        self.message_user(request,
                          __('Successfully published %(count)d %(name)s',
                             'Successfully published %(count)d %(plural_name)s',
                             len(queryset)) % dict(
                              count=len(queryset),
                              name=GameModel._meta.verbose_name,
                              plural_name=GameModel._meta.verbose_name_plural
                          ),
                          level=messages.INFO)

    mark_games_as_published.short_description = _('Mark selected as published')

    def mark_games_as_unpublished(self, request, queryset):
        queryset.update(published=False)
        self.message_user(request,
                          __('Successfully unpublished %(count)d %(name)s',
                             'Successfully unpublished %(count)d %(plural_name)s',
                             len(queryset)) % dict(
                              count=len(queryset),
                              name=GameModel._meta.verbose_name,
                              plural_name=GameModel._meta.verbose_name_plural
                          ),
                          level=messages.INFO)

    mark_games_as_unpublished.short_description = _('Mark selected as unpublished')

    def get_urls(self):
        info = self.model._meta.app_label, self.model._meta.model_name
        urls = [
            url(r'download-xbox/$', self.download_games_list_from_xbox, name='%s_%s_download_from_xbox' % info),
        ]
        urls.extend(super().get_urls())
        return urls
