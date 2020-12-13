package com.spbstu.reversemarket.profile.data.api

import com.spbstu.reversemarket.profile.data.model.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.*


interface UserApi {
    @GET("/user")
    fun getUser(): Observable<Response<UserBody>>

    @PUT("/user")
    fun editUser(@Body userBody: UserBody): Observable<Response<Void>>

    @GET("/user/addresses")
    fun getAddresses(): Observable<Response<List<AddressBodyWithId>>>

    @POST("/user/addresses")
    fun addAddress(@Body addressBodyNoId: AddressBodyNoId): Observable<Response<Void>>

    @PUT("/user/addresses/{id}")
    fun editAddress(
        @Path("id") id: Int,
        @Body addressBodyNoId: AddressBodyNoId
    ): Observable<Response<Void>>

    @GET("/user/favorites")
    fun getFavorites(): Observable<Response<List<Favorite>>>

    @POST("/user/favorites")
    fun setFavorite(@Body id: Int): Observable<Response<Void>>

    @GET("/user/favorites/{id}")
    fun getFavorite(@Path("id") id: Int): Observable<Response<Favorite>>

    @DELETE("/user/favorites/{id}")
    fun deleteFavorite(@Path("id") id: Int): Observable<Response<Void>>

    @GET("/user/orders_sell")
    fun getOrdersSell(): Observable<Response<List<Order>>>

    @GET("/user/orders_sell/{id}")
    fun getOrderSell(@Path("id") id: Int): Observable<Response<Order>>

    @GET("/user/orders_buy")
    fun getOrdersBuy(): Observable<Response<List<Order>>>

    @POST("/user/orders_buy")
    fun addOrderBuy(@Path("id") id: Int): Observable<Response<Void>>
}