package com.spbstu.reversemarket.profile.data.model

import com.google.gson.annotations.SerializedName
import com.spbstu.reversemarket.profile.domain.model.Address

fun AddressBodyWithId.toDomainModel(): Address = Address(
    id,
    name,
    region,
    city,
    street,
    number,
    building,
    apartment,
    zip,
    lastName,
    firstName,
    fatherName
)

data class AddressBodyWithId(
    @SerializedName("id")
    val id: Int,
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