package org.three.minutes.server

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SangleServiceImpl {
    private const val baseUrl = "http://52.78.217.66:8080"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service : SangleService = retrofit.create(SangleService::class.java)
}