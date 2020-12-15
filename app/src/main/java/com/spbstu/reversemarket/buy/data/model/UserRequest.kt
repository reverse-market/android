package com.spbstu.reversemarket.buy.data.model

import com.google.gson.annotations.SerializedName
import com.spbstu.reversemarket.filter.data.model.Tag

data class UserRequest (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("item_name")
    val itemName: String,
    @SerializedName("description")
    val photos: List<String>,
    @SerializedName("price")
    val price: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("tags")
    val tags: List<Tag>,
    @SerializedName("best_proposal")
    val bestProposal: Int,
    @SerializedName("is_favourite")
    val isFavorite: Boolean,
)