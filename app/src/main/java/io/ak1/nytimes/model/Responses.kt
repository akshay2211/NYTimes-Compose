package io.ak1.nytimes.model

import java.io.Serializable
import java.util.*

class StoriesResponse : Serializable {
    var status: String? = ""
    var last_updated: String? = ""
    var results: List<ResultsWrapper> = ArrayList()
}