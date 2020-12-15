package com.spbstu.reversemarket.product.data.api

import com.spbstu.reversemarket.product.data.model.BestProposalToBuyBody
import com.spbstu.reversemarket.profile.data.model.Order
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApi {

    @GET("/proposals/{id}")
    fun getBestProposal(
        @Path("id") id: Int,
    ): Observable<Response<Order>>

    @GET("/proposals/{id}")
    fun getUserBestProposal(
        @Path("id") id: Int,
    ): Observable<Response<Order>>

    @POST("/user/orders_buy")
    fun buyProposal(@Body body: BestProposalToBuyBody): Observable<Response<Void>>
}