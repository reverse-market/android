package com.spbstu.reversemarket.profile.presentation

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.profile.data.api.UserApi
import com.spbstu.reversemarket.profile.data.model.*
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
    private lateinit var boughtOrders: MutableLiveData<List<Order>>
    private lateinit var soldOrders: MutableLiveData<List<Order>>

    fun getUser(): LiveData<User> {
        userData = MutableLiveData()
        loadUser()
        return userData
    }

    fun getBought(): LiveData<List<Order>> {
        boughtOrders = MutableLiveData()
        loadBought()
        return boughtOrders
    }

    fun getSold(): LiveData<List<Order>> {
        soldOrders = MutableLiveData()
        loadSold()
        return soldOrders
    }

    fun getAddresses(): LiveData<List<AddressBodyWithId>> {
        if (!this::addressesData.isInitialized) {
            addressesData = MutableLiveData()
            loadAddressses()
        }

        return addressesData
    }

    fun changeName(name: String): LiveData<Boolean> {
        val res = MutableLiveData<Boolean>()
        userApi.editUser(UserBodyName(name))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                res.postValue(true)
            }, {
                res.postValue(false)
            })

        return res
    }

    private fun loadBought() {
        userApi.getOrdersBuy()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("WWWW", "Bought - $it")
                boughtOrders.postValue(it.body())
            }, {

            })
    }

    private fun loadSold() {
        userApi.getOrdersSell()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("WWWW", "Sold - $it")
                soldOrders.postValue(it.body())
            }, {

            })
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