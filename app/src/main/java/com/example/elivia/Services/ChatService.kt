package com.example.elivia.Services

import com.example.elivia.Models.Messages
import com.example.elivia.Models.RasaResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.POST
import retrofit2.http.Body

interface ChatService {
    @POST("/model/parse")
    fun postMessage(@Body body:Messages): Call<RasaResponse>

    companion object {
        private const val BASE_URL = "http://178.79.139.171:5005/"

        fun create(): ChatService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            return retrofit.create(ChatService::class.java)
        }
    }
}