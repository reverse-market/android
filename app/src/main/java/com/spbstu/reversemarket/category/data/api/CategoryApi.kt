package com.spbstu.reversemarket.category.data.api

import com.spbstu.reversemarket.category.data.model.Category
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET

interface CategoryApi {

    @GET("/categories")
    fun getCategories(): Observable<Response<List<Category>>>

}