package com.example.aer_app

import com.example.aer_app.models.Categories
import com.example.aer_app.models.Problems
import com.example.aer_app.models.Users
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://84.121.186.190:8091/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL)
        .build()

interface ApiService {

    @GET("usuarios/base/?format=json")
    fun getUsersData(): Call<MutableList<Users>>

    @GET("usuarios/base/{id}?format=json")
    fun getUserData(@Path("id") id: String): Call<Users>

    @GET("problemas/base/?format=json")
    fun getProblemsData(): Call<MutableList<Problems>>

    @GET("problemas/base/{id}?format=json")
    fun getProblemData(@Path("id") id: String): Call<Problems>

    @GET("categorias/base/?format=json")
    fun getCategoriesData(): Call<MutableList<Categories>>


}

object Api {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}