from rest_framework.serializers import ModelSerializer

from x360.models import RegionModel


class RegionModelSerializer(ModelSerializer):
    class Meta:
        model = RegionModel
        fields = ('id', 'name', 'code')
