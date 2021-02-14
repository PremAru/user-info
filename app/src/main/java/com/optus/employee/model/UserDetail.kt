package com.optus.employee.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserDetail (
    @SerializedName("albumId")
    var albumId: Int,

    @SerializedName("id")
    var id: String,

    @SerializedName("title")
    var title: String,

    @SerializedName("url")
    var url: String,

    @SerializedName("thumbnailUrl")
    var thumbnailUrl: String
): Serializable