from django.db import models
from django.utils.translation import gettext_lazy as _


class GameModel(models.Model):
    title = models.CharField(max_length=255, unique=True, verbose_name=_('Game title'))
    cover_link = models.URLField(verbose_name=_('URL to cover image'))
    store_link = models.URLField(unique=True, verbose_name=_('URL to Microsoft Store'))

    def save(self, force_insert=False, force_update=False, using=None, update_fields=None):
        self.full_clean()
        super().save(force_insert, force_update, using, update_fields)

    def __str__(self):
        return '{0}'.format(str(self.title))

    class Meta:
        verbose_name = _('Game')
        verbose_name_plural = _('Games')
