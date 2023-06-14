package com.example.messagingapp.messageService

import com.example.messagingapp.common.BasicAuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

private const val BASE_URL =
    "https://api.twilio.com/"

private val client = OkHttpClient.Builder()
    .addInterceptor(
        BasicAuthInterceptor(
            "AC68723c562382c362338c28ca54affe55",
            "ffaf50fba1ea505ae771d81b523351c8"
        )
    )
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface ApiService {
    @FormUrlEncoded
    @Headers("Authorization:Basic QUM2ODcyM2M1NjIzODJjMzYyMzM4YzI4Y2E1NGFmZmU1NSA6IGZmYWY1MGZiYTFlYTUwNWFlNzcxZDgxYjUyMzM1MWM4")
    @POST("2010-04-01/Accounts/AC68723c562382c362338c28ca54affe55/Messages.json")
    suspend fun postMessage(
        @Field("From") from: String,
        @Field("To") to: String,
        @Field("Body") message: String
    ): Response<Unit>
}

object Api {
    fun getInstance(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}