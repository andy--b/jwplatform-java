======================
JW Platform API Client
======================

A Java client library for accessing `JW Platform`_ API. Visit `JW Player Developer`_ site for more information about JW Platform API.

Installation
------------

JW Platform API library can be built using maven:

    $ mvn package

Usage
-----

Import the package:

    import com.andybowling.jwplatform.jwclient.JWClient;

Initialize ``jwplatform`` client instance (API key and secrets can be found in the JW Platform dashboard under the `account` tab):

    JWClient c = new JWClient(apiKey, apiSecret);

Make an API request. For this example `/videos/list`_ API resource is used:

    JSONObject response = c.request("videos/list");

If API request is successful, ``response`` variable will contain a JSONObject with information related to the response and the actual video data in ``response['video']``:

    {
      "total": 1,
      "rate_limit": {
        "limit": 60,
        "reset": 1517163840,
        "remaining": 59
      },
      "offset": 0,
      "limit": 50,
      "videos": [{
        "date": 1491425539,
        "expires_date": null,
        "author": null,
        "upload_session_id": null,
        "custom": {},
        "link": null,
        "description": "Great video, or the greatest video?",
        "title": "Lebron James Historic Block On Andre Iguodala From All Angles",
        "error": null,
        "tags": "awesome, cleveland, winners",
        "duration": "57.28",
        "sourceurl": null,
        "size": "54062409",
        "sourceformat": null,
        "sourcetype": "file",
        "updated": 1491425632,
        "mediatype": "video",
        "views": 15,
        "key": "burcMQxh",
        "status": "ready",
        "md5": "db41dc9d29a2c53c33189a0ef1379bf5"
      }],
      "status": "ok"
    }

JW Platform API library will raise exception inherited from ``com.andybowling.jwplatform.jwexceptions`` if ``response.get("status")`` is set to ``error``. For example, there is no ``/media/show`` API resource. Requesting it will raise ``com.andybowling.jwplatform.jwexceptions.JWPlatformNotFoundException``:

For the complete list of available exceptions see `JWPlatform/src/main/java/com/andybowling/jwplatform/JWExceptions.java`_ file.

Source Code
-----------

Source code for the JW Platform API library provided on `GitHub`_.

License
-------

JW Platform API library is distributed under the `MIT license`_.

.. _`JW Platform`: https://www.jwplayer.com/products/jwplatform/
.. _`JW Player Developer`: https://developer.jwplayer.com/jw-platform/reference/v1/
.. _`/videos/list`: https://developer.jwplayer.com/jw-platform/reference/v1/methods/videos/list.html
.. _`JWPlatform/src/main/java/com/andybowling/jwplatform/JWExceptions.java`: https://github.com/andy--b/jwplatform-java/blob/addreadme/src/main/java/com/andybowling/jwplatform/JWExceptions.java
.. _`MIT license`: https://github.com/jwplayer/jwplatform-py/blob/master/LICENSE
.. _`GitHub`: https://github.com/andy--b/jwplatform-java
