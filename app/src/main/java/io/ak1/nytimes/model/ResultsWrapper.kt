package io.ak1.nytimes.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResultsWrapper(
    //Make sure that the list contains a photo (if available), title and publication date / time of the article
    var title: String? = "",
    var url: String? = "",
    var published_date: String? = "",
    @SerializedName("abstract") var abstract_text: String? = "",
    var multimedia: ArrayList<Images>

) : Serializable
