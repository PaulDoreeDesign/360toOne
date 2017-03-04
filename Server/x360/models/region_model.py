from django.db import models
from django.utils.translation import gettext_lazy as _


class RegionModel(models.Model):
    name = models.CharField(max_length=255, verbose_name=_('Name'))
    code = models.CharField(max_length=2, unique=True, verbose_name=_('Region code'))

    def save(self, force_insert=False, force_update=False, using=None, update_fields=None):
        self.code = self.code.lower()
        self.full_clean()
        super().save(force_insert, force_update, using, update_fields)

    def __str__(self):
        return '{0} ({1})'.format(str(self.name), str(self.code))

    class Meta:
        verbose_name = _('Region')
        verbose_name_plural = _('Regions')
