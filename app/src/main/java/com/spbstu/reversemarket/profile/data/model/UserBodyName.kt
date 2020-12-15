package com.spbstu.reversemarket.profile.data.model

import com.google.gson.annotations.SerializedName

data class UserBodyName(
    @SerializedName("name")
    val name: String,
)