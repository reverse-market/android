package com.spbstu.reversemarket.login.data.api

import com.spbstu.reversemarket.login.data.model.SignInBody
import com.spbstu.reversemarket.login.data.model.SignInResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApi {
    @GET("/auth/check")
    fun checkAuth(): Observable<Response<Void>>

    @POST("/auth/sign_in")
    fun signIn(@Body signInBody: SignInBody): Observable<Response<SignInResponse>>
}