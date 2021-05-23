package io.ak1.nytimes.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class StoriesResponse : Serializable {
    var status: String? = ""
    var last_updated: String? = ""
    var results: List<ResultsWrapper> = ArrayList()
}

data class ResultsWrapper(
    //Make sure that the list contains a photo (if available), title and publication date / time of the article
    var title: String? = "",
    var url: String? = "",
    var byline: String? = "",
    var published_date: String? = "",
    @SerializedName("abstract") var abstract_text: String? = "",
    var multimedia: ArrayList<Images>,
    var des_facet: ArrayList<String>
) : Serializable

class Images : Serializable {
    var url: String? = ""
    var format: String? = ""
    var height: Int = 0
    var width: Int = 0
    var type: String? = ""
    var subtype: String? = ""
    var caption: String? = ""
    var copyright: String? = ""
}

@Entity(tableName = "results_table", indices = [Index(value = ["title"], unique = true)])
open class Results(
    //Make sure that the list contains a photo (if available), title and publication date / time of the article
    var title: String? = "",
    var url: String? = "",
    var published_date: String? = "",
    var url_thumb: String? = "",
    var url_large: String? = "",
    var type: String? = "",
    var height: Int = 0,
    var width: Int = 0,
    var byline: String? = "",
    var des_facet: String = "",
    @SerializedName("abstract") var abstract_text: String? = ""
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int = 0
    var bookmarked: Boolean = false

}

@Entity(tableName = "bookmarks_table", inheritSuperIndices = true)
class Bookmarks : Results()