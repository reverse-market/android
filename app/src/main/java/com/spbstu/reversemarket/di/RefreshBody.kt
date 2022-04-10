package com.spbstu.reversemarket.di

import com.google.gson.annotations.SerializedName

data class RefreshBody(
    @SerializedName("refresh_token")
    val refreshToken: String?
)