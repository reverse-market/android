package com.spbstu.reversemarket.profile.data.api

import com.spbstu.reversemarket.profile.data.model.AddressBodyNoId
import com.spbstu.reversemarket.profile.data.model.AddressBodyWithId
import com.spbstu.reversemarket.profile.data.model.UserBody
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.*


interface UserApi {
    @GET("/user")
    fun getUser(): Observable<Response<UserBody>>

    @PUT("/user")
    fun editUser(@Body userBody: UserBody): Observable<Response<Void>>

    @GET("/user/address")
    fun getAddresses(): Observable<Response<List<AddressBodyWithId>>>

    @POST("/user/address")
    fun addAddress(@Body addressBodyNoId: AddressBodyNoId): Observable<Response<Void>>

    @PUT("/user/addresses/{id}")
    fun editAddress(
        @Path("id") id: Int,
        @Body addressBodyNoId: AddressBodyNoId
    ): Observable<Response<Void>>
}