package io.ak1.nytimes.model

import java.io.Serializable

/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */
class BaseData : Serializable {
    var status: String? = ""
    var copyright: String? = ""
    var section: String? = ""
    var last_updated: String? = ""
    var num_results: Int = 0
    var results: List<ResultsWrapper> = ArrayList()

}

/*"status": "OK",
  "copyright": "Copyright (c) 2020 The New York Times Company. All Rights Reserved.",
  "section": "home",
  "last_updated": "2020-11-14T07:46:34-05:00",
  "num_results": 55,
  "results":*/