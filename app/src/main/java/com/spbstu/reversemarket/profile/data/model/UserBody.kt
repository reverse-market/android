package com.spbstu.reversemarket.profile.data.model

import com.google.gson.annotations.SerializedName
import com.spbstu.reversemarket.profile.domain.model.User

fun UserBody.toDomainModel(): User = User(name, email, photo, addressId)

data class UserBody(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("default_address_id")
    val addressId: Int,
)