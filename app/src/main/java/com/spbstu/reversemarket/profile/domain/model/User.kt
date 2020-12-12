package com.spbstu.reversemarket.profile.domain.model

data class User(
    val name: String,
    val email: String,
    val photo: String,
    val addressId: Int,
)