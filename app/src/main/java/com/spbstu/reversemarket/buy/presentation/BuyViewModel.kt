package com.spbstu.reversemarket.buy.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.buy.data.api.BuyApi
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.sell.data.model.Request
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@FeatureScope
class BuyViewModel @Inject constructor(
    private val api: BuyApi
) : ViewModel() {
    private lateinit var responses: MutableLiveData<List<Request>?>

    fun createRequest(isRefresh: Boolean): LiveData<List<Request>?> {
        if (!this::responses.isInitialized || isRefresh) {
            responses = MutableLiveData()
            api.getUserRequests()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccessful) {
                        responses.value = it.body() ?: emptyList()
                    }
                }, {
                    responses.value = null
                    Log.e(TAG, "createRequest: ", it)
                })

        }

        return responses
    }

    fun checkAuth() {
        api.checkAuth()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "checkAuth: success=${it.isSuccessful}")
            }, {
                Log.e(TAG, "checkAuth: ", it)
            })
    }

    companion object {
        private val TAG = BuyViewModel::class.simpleName
    }
}