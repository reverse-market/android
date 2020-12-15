package com.spbstu.reversemarket.profile.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressBodyNoId(
    @SerializedName("name")
    val name: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("number")
    val number: String,
    @SerializedName("building")
    val building: String,
    @SerializedName("appartment")
    val apartment: String,
    @SerializedName("zip")
    val zip: Int,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("father_name")
    val fatherName: String,
): Parcelable