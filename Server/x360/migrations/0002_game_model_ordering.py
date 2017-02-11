# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):
    dependencies = [
        ('x360', '0001_game_model'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='gamemodel',
            options={'ordering': ('title',), 'verbose_name': 'Game', 'verbose_name_plural': 'Games'},
        ),
    ]
