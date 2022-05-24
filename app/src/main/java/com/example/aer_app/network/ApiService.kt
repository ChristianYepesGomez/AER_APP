package com.example.aer_app

import com.example.aer_app.models.Users
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://84.121.186.190:8091/usuarios/base/?format=json"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL)
        .build()

interface ApiService {

    @GET("usuarios/base/?format=json")
    fun getUserData(): Call<List<Users>>

//    @GET("problemas/base/?format=json")
//    fun getProblemData(): Call<List<Problems>>
//
//    @GET("categories/base/?format=json")
//    fun getCategoriesData(): Call<List<Category>>


}

object Api {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}