package com.spbstu.reversemarket.buy.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.buy.data.api.BuyInfoDataApi
import com.spbstu.reversemarket.buy.data.model.Request
import com.spbstu.reversemarket.category.data.api.CategoryApi
import com.spbstu.reversemarket.category.data.model.Category
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.filter.data.model.Tag
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@FeatureScope
class BuyInfoViewModel @Inject constructor(
    private val api: BuyInfoDataApi,
    private val categoryApi: CategoryApi
) : ViewModel() {
    private lateinit var tagsData: MutableLiveData<List<Tag>>

    fun getTags(refresh: Boolean, category: Int = 8): LiveData<List<Tag>> {
        if (!this::tagsData.isInitialized) {
            tagsData = MutableLiveData()
        }
        if (refresh) {
            loadTags(category)
        }

        return tagsData
    }

    private lateinit var categoryData: MutableLiveData<List<Category>>

    fun getCategories(): LiveData<List<Category>> {
        if (!this::categoryData.isInitialized) {
            categoryData = MutableLiveData()
            loadCategories()
        }
        return categoryData
    }

    private fun loadCategories() {
        categoryApi.getCategories().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
            }
            .subscribe {
                if (it.code() == 200) {
                    categoryData.value = it.body()
                }
            }
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

    private fun loadTags(category: Int) {
        api.getTags(category)
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