package com.spbstu.reversemarket.product.data.model

import com.google.gson.annotations.SerializedName

data class Proposal(
    @SerializedName("id")
    val id: Int,
    @SerializedName("request_id")
    val requestId: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("photos")
    val photos: List<String>,
    @SerializedName("price")
    val price: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("username")
    val userName: String,
    @SerializedName("date")
    val date: String,
)