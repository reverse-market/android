package com.spbstu.reversemarket.sell.data.model

import com.google.gson.annotations.SerializedName

data class ProposalBody(
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
)