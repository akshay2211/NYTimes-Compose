package com.interview.thenewyorktimes.model

import java.io.Serializable

data class ResultsWrapper(
    //Make sure that the list contains a photo (if available), title and publication date / time of the article
    var title: String? = "",
    var url: String? = "",
    var published_date: String? = "",
    var abstract: String? = "",
    var multimedia: ArrayList<Images>

) : Serializable
