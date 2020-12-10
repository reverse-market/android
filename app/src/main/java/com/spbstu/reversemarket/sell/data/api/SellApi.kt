package com.spbstu.reversemarket.sell.data.api

import com.spbstu.reversemarket.filter.data.model.Tag
import com.spbstu.reversemarket.sell.data.model.Request
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface SellApi {

    @GET("/requests")
    fun getRequests(
        @QueryMap options: Map<String, String>
    ): Observable<Response<List<Request>>>

    @GET("/tag/{id}")
    fun getTag(@Path("id") id: Int): Observable<Response<Tag>>

}