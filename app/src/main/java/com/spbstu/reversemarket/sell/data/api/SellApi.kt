package com.spbstu.reversemarket.sell.data.api

import com.spbstu.reversemarket.profile.data.model.AddressBodyWithId
import com.spbstu.reversemarket.sell.data.model.ProposalBody
import com.spbstu.reversemarket.sell.data.model.Request
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface SellApi {

    @GET("/requests")
    fun getRequests(
        @QueryMap options: Map<String, String>
    ): Observable<Response<List<Request>>>

    @POST("/proposals")
    fun addProposal(@Body proposalBody: ProposalBody): Observable<Response<Void>>

    @GET("/user/address")
    fun getAddresses(): Observable<Response<List<AddressBodyWithId>>>

}