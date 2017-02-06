# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):
    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='GameModel',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('title', models.CharField(max_length=255, unique=True, verbose_name='Game title')),
                ('cover_link', models.URLField(verbose_name='URL to cover image')),
                ('store_link', models.URLField(unique=True, verbose_name='URL to Microsoft Store')),
            ],
            options={
                'verbose_name_plural': 'Games',
                'verbose_name': 'Game',
            },
        ),
    ]
