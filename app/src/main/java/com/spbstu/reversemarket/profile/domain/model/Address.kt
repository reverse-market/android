package com.spbstu.reversemarket.profile.domain.model

data class Address(
    val id: Int? = null,
    val name: String,
    val region: String,
    val city: String,
    val street: String,
    val number: Int,
    val building: String,
    val apartment: Int,
    val zip: String,
    val lastName: String,
    val firstName: String,
    val fatherName: String,
)