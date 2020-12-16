package com.example.wakeup.network

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitInterface {
    @POST("register")
    @FormUrlEncoded
    fun registerUser(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") pw: String
    ): Observable<String>

    @POST("login")
    @FormUrlEncoded
    fun loginUser(
        @Field("email") email: String,
        @Field("password") pw: String
    ): Observable<String>
}