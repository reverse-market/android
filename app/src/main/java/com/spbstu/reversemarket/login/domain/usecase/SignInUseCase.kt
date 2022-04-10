package com.spbstu.reversemarket.login.domain.usecase

import com.spbstu.reversemarket.login.data.model.SignInBody
import com.spbstu.reversemarket.login.data.model.SignInResponse
import com.spbstu.reversemarket.login.domain.repository.LoginRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    operator fun invoke(signInBody: SignInBody): Observable<SignInResponse?> {
        return loginRepository.signIn(signInBody).map { it?.body() }
    }
}