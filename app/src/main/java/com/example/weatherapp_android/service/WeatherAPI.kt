package com.example.weatherapp_android.service

import com.example.weatherapp_android.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {


    @GET(APIUrl.WEATHER_LIST)
    fun getData(
        @Query("q") cityName: String
    ): Single<WeatherModel>
}