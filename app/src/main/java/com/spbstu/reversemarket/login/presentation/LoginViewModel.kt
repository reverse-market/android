package com.spbstu.reversemarket.login.presentation

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.login.data.model.SignInBody
import com.spbstu.reversemarket.login.domain.usecase.CheckAuthUseCase
import com.spbstu.reversemarket.login.domain.usecase.SignInUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@FeatureScope
class LoginViewModel @Inject constructor(
    private val authUseCase: CheckAuthUseCase,
    private val signInUseCase: SignInUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val state: MutableLiveData<State> = MutableLiveData(State.INITIAL)

    fun getState(): LiveData<State> = state

    fun signIn(token: String) {
        signInUseCase.invoke(SignInBody(token))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.let {
                    state.value = State.SUCCESS
                    sharedPreferences.edit().putString("token", it.jwtToken).apply()
                    sharedPreferences.edit().putString("refresh", it.refreshToken).apply()
                }
            }, {
                Log.e(TAG, "signIn: ", it)
            })
    }

    fun checkAuth() {
        authUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it) {
                    state.value = State.SUCCESS
                } else {
                    state.value = State.ERROR
                }
            }, {
                Log.e(TAG, "checkAuth: ", it)
            })
    }

    enum class State {
        INITIAL,
        SUCCESS,
        ERROR
    }

    companion object {
        private val TAG = LoginViewModel::class.simpleName
    }
}