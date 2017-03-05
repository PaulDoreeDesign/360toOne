from django.contrib import admin
from django.contrib.admin import register

from x360.models import RegionModel


@register(RegionModel)
class RegionModelAdmin(admin.ModelAdmin):
    pass
