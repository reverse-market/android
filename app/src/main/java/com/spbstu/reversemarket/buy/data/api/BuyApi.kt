package com.spbstu.reversemarket.buy.data.api

import com.spbstu.reversemarket.sell.data.model.Request
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET

interface BuyApi {
    @GET("/user/requests")
    fun getUserRequests(): Observable<Response<List<Request>>>

    @GET("/check")
    fun checkAuth(): Observable<Response<Void>>
}