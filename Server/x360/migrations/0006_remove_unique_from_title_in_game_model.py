# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):
    dependencies = [
        ('x360', '0005_game_and_region_relations'),
    ]

    operations = [
        migrations.AlterField(
            model_name='gamemodel',
            name='title',
            field=models.CharField(max_length=255, verbose_name='Game title'),
        ),
    ]
