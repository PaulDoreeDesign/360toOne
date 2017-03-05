# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):
    dependencies = [
        ('x360', '0004_region_model'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='regionmodel',
            options={'ordering': ('name',), 'verbose_name': 'Region', 'verbose_name_plural': 'Regions'},
        ),
        migrations.AddField(
            model_name='gamemodel',
            name='available_regions',
            field=models.ManyToManyField(default=None, related_name='available', to='x360.RegionModel',
                                         verbose_name='Available regions'),
        ),
        migrations.AddField(
            model_name='gamemodel',
            name='excluded_regions',
            field=models.ManyToManyField(default=None, related_name='excluded', to='x360.RegionModel',
                                         verbose_name='Excluded regions'),
        ),
        migrations.AlterField(
            model_name='regionmodel',
            name='code',
            field=models.CharField(max_length=2, unique=True, verbose_name='Region code'),
        ),
    ]
