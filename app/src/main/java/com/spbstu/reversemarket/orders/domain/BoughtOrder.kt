package com.spbstu.reversemarket.orders.domain

data class BoughtOrder(
    val title: String,
    val image: String,
    val description: String,
    val amount: Int,
    val price: Int
)