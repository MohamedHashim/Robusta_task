package com.mohamedhashim.robusta_task.api.service

import com.mohamedhashim.robusta_task.data.response.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Mohamed Hashim on 11/8/2020.
 */
interface WeatherService {

    @GET("weather")
    fun fetchWeather(): Call<WeatherResponse>
}