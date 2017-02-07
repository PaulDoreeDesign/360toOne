from django.conf.urls import include, url
from rest_framework.routers import SimpleRouter

from x360.views.api import GameModelApiViewSet

router = SimpleRouter()
router.register(r'games', GameModelApiViewSet)
urlpatterns = [
    url(r'^api/', include(router.urls))
]
