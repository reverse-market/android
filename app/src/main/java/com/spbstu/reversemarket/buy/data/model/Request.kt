package com.spbstu.reversemarket.buy.data.model

import com.google.gson.annotations.SerializedName
import com.spbstu.reversemarket.filter.data.model.Tag

data class Request(
    @SerializedName("name")
    val name: String,
    @SerializedName("item_name")
    val itemName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("photos")
    val photos: List<String>,
    @SerializedName("price")
    val price: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("tags")
    val tags: List<Tag>
)