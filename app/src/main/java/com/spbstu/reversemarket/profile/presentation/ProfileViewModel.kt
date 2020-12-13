package com.spbstu.reversemarket.profile.presentation

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.profile.data.api.UserApi
import com.spbstu.reversemarket.profile.data.model.AddressBodyNoId
import com.spbstu.reversemarket.profile.data.model.AddressBodyWithId
import com.spbstu.reversemarket.profile.data.model.toDomainModel
import com.spbstu.reversemarket.profile.domain.model.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@FeatureScope
class ProfileViewModel @Inject constructor(
    private val userApi: UserApi,
    private val sharedPreferences: SharedPreferences
) :
    ViewModel() {
    private lateinit var userData: MutableLiveData<User>
    private lateinit var addressesData: MutableLiveData<List<AddressBodyWithId>>

    fun getUser(): LiveData<User> {
        if (!this::userData.isInitialized) {
            userData = MutableLiveData()
            loadUser()
        }
        return userData
    }

    fun getAddresses(): LiveData<List<AddressBodyWithId>> {
        if (!this::addressesData.isInitialized) {
            addressesData = MutableLiveData()
            loadAddressses()
        }

        return addressesData
    }

    private fun loadAddressses() {
        userApi.getAddresses()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("WWWW", "Address response - $it")
                addressesData.postValue(it.body())
            }, {

            })
    }

    fun addAddress(body: AddressBodyNoId): LiveData<Boolean> {
        val res = MutableLiveData<Boolean>()
        userApi.addAddress(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("WWWW", "Add address response = $it")
                if (it.isSuccessful) {
                    res.postValue(true)
                } else {
                    res.postValue(false)
                }
            }, {

            })

        return res
    }

    fun editAddress(body: AddressBodyWithId): LiveData<Boolean> {
        val res = MutableLiveData<Boolean>()
        val id = body.id
        val bodyNoId = AddressBodyNoId(
            body.name,
            body.region,
            body.city,
            body.street,
            body.number,
            body.building,
            body.apartment,
            body.zip,
            body.lastName,
            body.firstName,
            body.fatherName
        )
        userApi.editAddress(id, bodyNoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("WWWW", "Edit address response = $it")
                if (it.isSuccessful) {
                    res.postValue(true)
                } else {
                    res.postValue(false)
                }
            }, {

            })

        return res
    }

    fun signOut() {
        sharedPreferences.edit().putString("token", "").apply()
    }

    private fun loadUser() {
        userApi.getUser().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
            }
            .subscribe {
                if (it.code() == 200) {
                    userData.value = it.body()!!.toDomainModel()
                }
            }
    }
}