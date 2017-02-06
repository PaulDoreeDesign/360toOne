from django.db import models
from django.utils.translation import gettext_lazy as _


class GameModel(models.Model):
    title = models.CharField(max_length=255, unique=True, verbose_name=_('Game title'))
    cover_link = models.URLField(verbose_name=_('URL to cover image'))
    store_link = models.URLField(unique=True, verbose_name=_('URL to Microsoft Store'))

    class Meta:
        verbose_name = _('Game')
        verbose_name_plural = _('Games')
