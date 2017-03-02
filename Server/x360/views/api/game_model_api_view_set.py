from rest_framework.viewsets import ReadOnlyModelViewSet

from x360.models import GameModel
from x360.serializers import GameModelDetailSerializer, GameModelListSerializer


class GameModelApiViewSet(ReadOnlyModelViewSet):
    queryset = GameModel.objects.filter(published=True)

    def get_serializer_class(self):
        if self.action == 'list':
            return GameModelListSerializer
        elif self.action == 'retrieve':
            return GameModelDetailSerializer
        raise ValueError
