package com.spbstu.reversemarket.sell.domain.model

data class Product(
    val name: String,
    val fullName: String,
    val viewAmount: Long,
    val tags: List<String>,
    val description: String,
    val price: String,
    val username: String,
    val date: String
)
