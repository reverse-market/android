package com.spbstu.reversemarket.profile.data.model

import com.google.gson.annotations.SerializedName

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
    val number: Int,
    @SerializedName("building")
    val building: String,
    @SerializedName("apartment")
    val apartment: Int,
    @SerializedName("zip")
    val zip: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("father_name")
    val fatherName: String,
)