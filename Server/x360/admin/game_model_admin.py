from django.contrib import admin
from django.contrib.admin import register

from x360.models import GameModel


@register(GameModel)
class GameModelAdmin(admin.ModelAdmin):
    pass
