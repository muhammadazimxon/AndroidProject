package com.example.retrofitpractice.data

import com.example.retrofitpractice.models.CatFacts
import retrofit2.http.GET
import retrofit2.Response

interface ApiInterface {
    @GET("/fact")
    suspend fun getRandomFact(): Response<CatFacts>
}