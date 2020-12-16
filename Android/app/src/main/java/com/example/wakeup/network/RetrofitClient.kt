package com.example.wakeup.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitClient() {
    var instance: Retrofit? = null

    fun Instance(): Retrofit? {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl("http://10.53.68.141.3000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return instance!!
    }
}