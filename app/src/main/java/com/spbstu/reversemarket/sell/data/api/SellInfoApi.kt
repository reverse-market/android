package com.spbstu.reversemarket.sell.data.api

import com.spbstu.reversemarket.buy.data.model.ImageResponse
import com.spbstu.reversemarket.sell.data.model.ProposalBody
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SellInfoApi {

    @POST("/proposals")
    fun addProposal(@Body proposalBody: ProposalBody): Observable<Response<Void>>

    @Multipart
    @POST("/images")
    fun uploadImage(
        @Part file: MultipartBody.Part
    ): Observable<Response<ImageResponse>>

}