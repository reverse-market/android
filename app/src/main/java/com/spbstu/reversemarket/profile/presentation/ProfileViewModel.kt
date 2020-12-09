package com.spbstu.reversemarket.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.profile.data.api.UserApi
import com.spbstu.reversemarket.profile.data.model.toDomainModel
import com.spbstu.reversemarket.profile.domain.model.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@FeatureScope
class ProfileViewModel @Inject constructor(private val userApi: UserApi) :
    ViewModel() {
    private lateinit var userData: MutableLiveData<User>

    fun getUser(): LiveData<User> {
        if (!this::userData.isInitialized) {
            userData = MutableLiveData()
            loadUser()
        }
        return userData
    }

    private fun loadUser() {
        userApi.getUser().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.code() == 200) {
                    userData.value = it.body()!!.toDomainModel()
                }
            }
    }
}