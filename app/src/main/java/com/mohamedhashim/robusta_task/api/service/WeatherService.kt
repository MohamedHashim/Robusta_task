package com.mohamedhashim.robusta_task.api

import com.mohamedhashim.robusta_task.BuildConfig
import com.mohamedhashim.robusta_task.data.response.WeatherResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Mohamed Hashim on 11/8/2020.
 */
interface WeatherService {

    @GET("data/2.5/weather")
    fun getWeather(
        @Query("q") query: String,
        @Query("appid") appId: String = BuildConfig.openWeatherMap_api_key): Call<WeatherResponse>


    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/"
        fun create(): WeatherService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherService::class.java)
        }
    }
}