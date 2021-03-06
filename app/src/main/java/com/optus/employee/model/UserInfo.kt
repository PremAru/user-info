package com.optus.employee.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserInfo (
    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("phone")
    var phone: String,

    @SerializedName("email")
    var email: String
): Serializable