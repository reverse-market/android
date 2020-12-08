package com.spbstu.reversemarket.login.domain.usecase

import com.spbstu.reversemarket.login.domain.repository.LoginRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    operator fun invoke(): Observable<Boolean> {
        return loginRepository.check()
            .map {
                it.code() == 200
            }
    }
}