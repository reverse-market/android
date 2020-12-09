package com.spbstu.reversemarket.category.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.category.data.api.CategoryApi
import com.spbstu.reversemarket.category.data.model.Category
import com.spbstu.reversemarket.di.scope.FeatureScope
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@FeatureScope
class CategoryViewModel @Inject constructor(
    private val categoryApi: CategoryApi
) : ViewModel() {
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

}