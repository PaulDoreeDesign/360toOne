from django.conf.urls import include, url
from rest_framework.routers import SimpleRouter

from x360.views.api import GameModelApiViewSet, RegionModelApiViewSet

router = SimpleRouter()
router.register(r'games', GameModelApiViewSet)
router.register(r'regions', RegionModelApiViewSet)
urlpatterns = [
    url(r'^api/', include(router.urls))
]
