package com.spbstu.reversemarket.login.domain.usecase

import android.util.Log
import com.spbstu.reversemarket.login.data.api.LoginApi
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(private val loginApi: LoginApi) {
    operator fun invoke(): Observable<Boolean> {
        return loginApi.checkAuth()
            .map {
                it.code() == 200
            }
    }
}