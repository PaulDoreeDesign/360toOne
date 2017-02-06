from django.core.exceptions import ValidationError
from django.test import TestCase

from x360.models import GameModel


class TestGameModel(TestCase):
    def setUp(self):
        self.game_title = 'Game'
        self.cover_link = 'http://www.game.com/cover.jpg'
        self.store_link = 'http://www.game.com/store/game/'

    def test_string_representation(self):
        """String representation of game is its title"""
        game = GameModel.objects.create(title=self.game_title, cover_link=self.cover_link, store_link=self.store_link)
        self.assertEqual(self.game_title, str(game), msg='Title should be string representation of game object')

    def test_game_without_title(self):
        """Each game must have title"""
        with self.assertRaises(ValidationError, msg='Creation of game without title must raise ValidationError'):
            GameModel.objects.create(cover_link=self.cover_link, store_link=self.store_link)

    def test_game_without_cover_link(self):
        """Each game must have cover link"""
        with self.assertRaises(ValidationError, msg='Creation of game without cover link must raise ValidationError'):
            GameModel.objects.create(title=self.game_title, store_link=self.store_link)

    def test_game_without_store_link(self):
        """Each game must have store link"""
        with self.assertRaises(ValidationError, msg='Creation of game without store link must raise ValidationError'):
            GameModel.objects.create(title=self.game_title, cover_link=self.store_link)
