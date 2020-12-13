package com.spbstu.reversemarket.profile.domain.model

data class Address(
    val id: Int? = null,
    val name: String,
    val region: String,
    val city: String,
    val street: String,
    val number: String,
    val building: String,
    val apartment: String,
    val zip: Int,
    val lastName: String,
    val firstName: String,
    val fatherName: String,
)