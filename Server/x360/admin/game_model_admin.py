
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
