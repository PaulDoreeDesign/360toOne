from rest_framework.viewsets import ReadOnlyModelViewSet

from x360.models import RegionModel
from x360.serializers import RegionModelSerializer


class RegionModelApiViewSet(ReadOnlyModelViewSet):
    queryset = RegionModel.objects.all()
    serializer_class = RegionModelSerializer
