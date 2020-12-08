package com.spbstu.reversemarket.login.domain.repository

import com.spbstu.reversemarket.login.data.model.SignInBody
import com.spbstu.reversemarket.login.data.model.SignInResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

interface LoginRepository {
    fun check(): Observable<Response<Void>>
    fun signIn(token: SignInBody): Observable<Response<SignInResponse>>
}