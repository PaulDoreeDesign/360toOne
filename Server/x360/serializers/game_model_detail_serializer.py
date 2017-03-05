from rest_framework.serializers import ModelSerializer

from x360.models import GameModel


class GameModelDetailSerializer(ModelSerializer):
    class Meta:
        model = GameModel
        fields = ('id', 'title', 'cover_link', 'store_link', 'available_regions', 'excluded_regions')
