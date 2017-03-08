from rest_framework.serializers import ModelSerializer

from .region_model_serializer import RegionModelSerializer
from x360.models import GameModel


class GameModelDetailSerializer(ModelSerializer):
    available_regions = RegionModelSerializer(many=True, read_only=True)
    excluded_regions = RegionModelSerializer(many=True, read_only=True)

    class Meta:
        model = GameModel
        fields = ('id', 'title', 'cover_link', 'store_link', 'available_regions', 'excluded_regions')
