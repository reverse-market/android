package com.spbstu.reversemarket.buy.data.api

import com.spbstu.reversemarket.buy.data.model.Request
import com.spbstu.reversemarket.filter.data.model.Tag
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BuyInfoDataApi {
    @POST("/user/requests")
    fun createRequest(@Body request: Request): Observable<Response<Void>>

    @GET("/tags")
    fun getTags(): Observable<Response<List<Tag>>>
}