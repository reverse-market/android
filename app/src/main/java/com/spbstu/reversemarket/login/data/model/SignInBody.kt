package com.spbstu.reversemarket.login.data.model

import com.google.gson.annotations.SerializedName

data class SignInBody(
    @SerializedName("id_token")
    val idToken: String
)