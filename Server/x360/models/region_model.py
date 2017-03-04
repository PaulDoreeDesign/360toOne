from django.db import models
from django.utils.translation import gettext_lazy as _


class RegionModel(models.Model):
    name = models.CharField(max_length=255, verbose_name=_('Name'))
    code = models.CharField(max_length=2, verbose_name=_('Region code'))

    class Meta:
        verbose_name = _('Region')
        verbose_name_plural = _('Regions')
