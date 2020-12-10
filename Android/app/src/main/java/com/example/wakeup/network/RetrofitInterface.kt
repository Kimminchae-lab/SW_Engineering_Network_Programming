package com.example.wakeup.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitInterface {
    @POST("/login")
    fun executeLogin(@Body map: HashMap<String, String>): Call<LoginResult>

    @POST("/signup")
    fun executeSignup(@Body map: HashMap<String, String>): Call<Void>
}