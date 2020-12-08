package com.spbstu.reversemarket.login.domain.usecase

import com.spbstu.reversemarket.login.data.api.LoginApi
import com.spbstu.reversemarket.login.data.model.SignInBody
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val loginApi: LoginApi) {
    operator fun invoke(signInBody: SignInBody): Observable<String?> {
        return loginApi.signIn(signInBody).map { it?.body()?.jwtToken }
    }
}