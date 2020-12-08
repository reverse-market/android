package com.spbstu.reversemarket.login.data.model

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("jwt_token")
    val jwtToken: String
)