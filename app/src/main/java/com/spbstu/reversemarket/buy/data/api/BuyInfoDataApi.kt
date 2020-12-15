package com.spbstu.reversemarket.buy.data.api

import com.spbstu.reversemarket.buy.data.model.ImageResponse
import com.spbstu.reversemarket.buy.data.model.Request
import com.spbstu.reversemarket.filter.data.model.Tag
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface BuyInfoDataApi {
    @POST("/user/requests")
    fun createRequest(@Body request: Request): Observable<Response<Void>>

    @GET("/tags")
    fun getTags(@Query("category") category: Int): Observable<Response<List<Tag>>>

    @Multipart
    @POST("/images")
    fun uploadImage(
        @Part file: MultipartBody.Part
    ): Observable<Response<ImageResponse>>
}