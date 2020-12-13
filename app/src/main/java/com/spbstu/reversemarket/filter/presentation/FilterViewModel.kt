package com.spbstu.reversemarket.filter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.filter.data.api.FilterApi
import com.spbstu.reversemarket.filter.data.model.Tag
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@FeatureScope
class FilterViewModel @Inject constructor(
    private val filterApi: FilterApi
) : ViewModel() {
    private lateinit var tagsData: MutableLiveData<List<Tag>>

    fun getTags(categoryId: Int): LiveData<List<Tag>> {
        if (!this::tagsData.isInitialized) {
            tagsData = MutableLiveData()
            loadTags(categoryId)
        }
        return tagsData
    }

    private fun loadTags(categoryId: Int) {
        filterApi.getTags(categoryId, "").subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
            }
            .subscribe {
                if (it.code() == 200) {
                    tagsData.value = it.body()
                }
            }
    }

}