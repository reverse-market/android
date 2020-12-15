package com.spbstu.reversemarket.filter.data.api

import com.spbstu.reversemarket.filter.data.model.Tag
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilterApi {

    @GET("/tags")
    fun getTags(
        @Query("category") category: Int,
        @Query("search") search: String
    ): Observable<Response<List<Tag>>>

}