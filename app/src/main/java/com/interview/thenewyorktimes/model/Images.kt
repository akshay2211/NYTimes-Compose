package com.interview.thenewyorktimes.model

import java.io.Serializable

class Images : Serializable {
    var url: String? = ""
    var format: String? = ""
    var height: Int = 0
    var width: Int = 0
    var type: String? = ""
    var subtype: String? = ""
    var caption: String? = ""
    var copyright: String? = ""


/* "url": "https://static01.nyt.com/images/2020/11/15/us/14virus-deaths-print5-sub/merlin_179994312_6f26c493-db39-4012-96c7-fab041804e3e-superJumbo.jpg",
          "format": "superJumbo",
          "height": 1366,
          "width": 2048,
          "type": "image",
          "subtype": "photo",
          "caption": "Transporting a patient at Del Sol Medical Center in El Paso on Friday.",
          "copyright": "Joel Angel Juarez for The New York Times"*/
}
