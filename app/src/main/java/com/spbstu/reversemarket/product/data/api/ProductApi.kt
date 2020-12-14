package com.spbstu.reversemarket.product.data.api

import com.spbstu.reversemarket.product.data.model.Proposal
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("/proposals/{id}")
    fun getBestProposal(
        @Path("id") id: Int,
    ): Observable<Response<Proposal>>

    @GET("/user/proposals/{id}")
    fun getUserBestProposal(
        @Path("id") id: Int,
    ): Observable<Response<Proposal>>

}