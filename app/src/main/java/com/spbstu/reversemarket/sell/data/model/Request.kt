package com.spbstu.reversemarket.sell.data.model

import com.google.gson.annotations.SerializedName

data class Request(
    @SerializedName("id")
    val id: Int,
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
    @SerializedName("username")
    val userName: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("tags")
    val tags: List<Int>,
    @SerializedName("best_proposal")
    val bestProposal: Int,
    @SerializedName("is_favorite")
    val isFavorite: Boolean,
)