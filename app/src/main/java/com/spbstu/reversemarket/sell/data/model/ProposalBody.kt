package com.spbstu.reversemarket.sell.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProposalBody(
    @SerializedName("request_id")
    val requestId: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("photos")
    var photos: List<String>,
    @SerializedName("price")
    val price: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("date")
    val date: String
) : Parcelable