package com.spbstu.reversemarket.buy.presentation

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
class BuyViewModel @Inject constructor(private val api: BuyApi) : ViewModel() {
    private lateinit var responses: MutableLiveData<List<Request>>

    fun createRequest(isRefersh: Boolean): LiveData<List<Request>> {
        if (!this::responses.isInitialized || isRefersh) {
            responses = MutableLiveData()
            api.getUserRequests()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.isSuccessful) {
                        responses.value = it.body()
                    }
                }

        }

        return responses
    }
}