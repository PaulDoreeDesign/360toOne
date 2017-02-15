from django.conf.urls import url
from django.contrib import admin
from django.contrib.admin import register
from django.shortcuts import HttpResponseRedirect
from django.urls import reverse

from x360.models import GameModel


@register(GameModel)
class GameModelAdmin(admin.ModelAdmin):
    def download_games_list_from_xbox(self, request):
        info = self.model._meta.app_label, self.model._meta.model_name
        return HttpResponseRedirect(reverse('admin:%s_%s_changelist' % info))

    def get_urls(self):
        info = self.model._meta.app_label, self.model._meta.model_name
        urls = [
            url(r'download-xbox/$', self.download_games_list_from_xbox, name='%s_%s_download_from_xbox' % info),
        ]
        urls.extend(super().get_urls())
        return urls
