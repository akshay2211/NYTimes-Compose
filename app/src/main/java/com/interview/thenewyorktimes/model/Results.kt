package com.interview.thenewyorktimes.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "results_table", indices = [Index(value = ["title"], unique = true)])
data class Results(
    //Make sure that the list contains a photo (if available), title and publication date / time of the article
    var title: String? = "",
    var url: String? = "",
    var published_date: String? = "",
    var url_thumb: String? = "",
    var url_large: String? = "",
    var type: String? = "",
    var height: Int = 0,
    var width: Int = 0,
    @SerializedName("abstract") var abstract_text: String? = ""
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int = 0

}
