package com.example.retrofit_launchedeffect_navigation_practice.data

import com.example.retrofit_launchedeffect_navigation_practice.models.AllMemesData
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("get_memes")
    suspend fun getMemesList() : Response<AllMemesData>
}