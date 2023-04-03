from django.conf.urls import url,include
import views

urlpatterns=[
    url(r'^',include('HYapi.urls')),
]