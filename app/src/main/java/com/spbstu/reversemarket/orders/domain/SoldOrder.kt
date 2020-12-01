package com.spbstu.reversemarket.orders.domain

data class SoldOrder(
    val title: String,
    val image: String,
    val description: String,
    val amount: Int,
    val price: Int
)