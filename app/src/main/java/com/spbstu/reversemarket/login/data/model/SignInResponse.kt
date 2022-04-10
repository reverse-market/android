package com.spbstu.reversemarket.login.data.model

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("access_token")
    val jwtToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
)