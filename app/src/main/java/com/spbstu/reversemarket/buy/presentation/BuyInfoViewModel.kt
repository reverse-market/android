package com.spbstu.reversemarket.buy.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.buy.data.api.BuyInfoDataApi
import com.spbstu.reversemarket.buy.data.model.Request
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.filter.data.model.Tag
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@FeatureScope
class BuyInfoViewModel @Inject constructor(private val api: BuyInfoDataApi) : ViewModel() {
    private lateinit var tagsData: MutableLiveData<List<Tag>>

    fun getTags(): LiveData<List<Tag>> {
        if (!this::tagsData.isInitialized) {
            tagsData = MutableLiveData()
            loadTags()
        }

        return tagsData
    }

    fun createRequest(request: Request): LiveData<Boolean> {
        val res = MutableLiveData(false)
        api.createRequest(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.code() == 200) {
                    res.postValue(true)
                } else {
                    res.postValue(false)
                }
            }
        return res
    }

    private fun loadTags() {
        api.getTags()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isSuccessful) {
                    tagsData.postValue(it.body())
                } else {
                    tagsData.postValue(listOf())
                }
            }
    }
}