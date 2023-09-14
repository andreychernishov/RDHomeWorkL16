package com.example.rdhomeworkl16

import io.reactivex.Single
import retrofit2.http.GET

interface ApiInterface {
    @GET("/superhero-api/api/all.json")
    fun getMemes(): Single<HeroResponce>
}