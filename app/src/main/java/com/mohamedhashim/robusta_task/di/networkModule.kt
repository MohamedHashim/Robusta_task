package com.mohamedhashim.robusta_task.di

import com.mohamedhashim.robusta_task.api.Endpoint
import com.mohamedhashim.robusta_task.api.RequestInterceptor
import com.mohamedhashim.robusta_task.api.client.WeatherClient
import com.mohamedhashim.robusta_task.api.service.WeatherService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Mohamed Hashim on 11/8/2020.
 */
val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(Endpoint.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(WeatherService::class.java) }

    single { WeatherClient(get()) }
}
