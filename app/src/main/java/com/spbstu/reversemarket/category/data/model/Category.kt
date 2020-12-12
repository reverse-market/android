package com.spbstu.reversemarket.category.data.model

import com.google.gson.annotations.SerializedName

data class Category (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo")
    val photo: String
)