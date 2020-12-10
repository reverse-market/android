package com.spbstu.reversemarket.sell.domain.model

import com.spbstu.reversemarket.filter.data.model.Tag

data class Product(
    val name: String,
    val fullName: String,
    val viewAmount: Long,
    val tags: List<Tag>,
    val description: String,
    val price: String,
    val username: String,
    val date: String
)
