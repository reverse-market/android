package com.spbstu.reversemarket.login.data.repository

import com.spbstu.reversemarket.login.data.api.LoginApi
import com.spbstu.reversemarket.login.data.model.SignInBody
import com.spbstu.reversemarket.login.data.model.SignInResponse
import com.spbstu.reversemarket.login.domain.repository.LoginRepository
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class LoginRepositoryImpl(private val loginApi: LoginApi): LoginRepository {
    override fun check(): Observable<Response<Void>> {
        return loginApi.checkAuth()
    }

    override fun signIn(token: SignInBody): Observable<Response<SignInResponse>> {
        return loginApi.signIn(token)
    }
}