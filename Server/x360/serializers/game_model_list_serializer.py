from rest_framework.serializers import ModelSerializer

from x360.models import GameModel


class GameModelListSerializer(ModelSerializer):
    class Meta:
        model = GameModel
        fields = ('id', 'title', 'cover_link')
