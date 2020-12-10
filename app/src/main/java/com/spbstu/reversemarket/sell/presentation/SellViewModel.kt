package com.spbstu.reversemarket.sell.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.filter.data.model.Tag
import com.spbstu.reversemarket.sell.data.api.SellApi
import com.spbstu.reversemarket.sell.data.model.Request
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@FeatureScope
class SellViewModel @Inject constructor(
    private val sellApi: SellApi
) : ViewModel() {
    private lateinit var requestData: MutableLiveData<List<Request>>

    fun getRequests(
        page: Int,
        size: Int,
        category: Int,
        tags: List<Tag>,
        priceFrom: Int,
        priceTo: Int,
        sort: String,
        search: String
    ): LiveData<List<Request>> {
        if (!this::requestData.isInitialized) {
            requestData = MutableLiveData()
            loadRequests(page, size, category, tags, priceFrom, priceTo, sort, search)
        }
        return requestData
    }

    private fun loadRequests(
        page: Int,
        size: Int,
        category: Int,
        tags: List<Tag>,
        priceFrom: Int,
        priceTo: Int,
        sort: String,
        search: String
    ) {
        sellApi.getRequests(
            formQueryMap(page, size, category, tags, priceFrom, priceTo, sort, search)
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
            }
            .subscribe {
                if (it.code() == 200) {
                    requestData.value = it.body()
                }
            }
    }

    private fun formQueryMap(
        page: Int,
        size: Int,
        category: Int,
        tags: List<Tag>,
        priceFrom: Int,
        priceTo: Int,
        sort: String,
        search: String
    ): Map<String, String> {
        val queryMap: MutableMap<String, String> = mutableMapOf()
        queryMap["page"] = page.toString()
        queryMap["size"] = size.toString()
        queryMap["category"] = category.toString()
        tags.forEach {
            queryMap["tag"] = it.id.toString()
        }
        queryMap["tags"]
        queryMap["price_from"] = priceFrom.toString()
        queryMap["price_to"] = priceTo.toString()
        queryMap["sort"] = sort
        queryMap["search"] = search
        return queryMap
    }

}