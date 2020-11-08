package com.mohamedhashim.robusta_task.api

import com.mohamedhashim.robusta_task.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Mohamed Hashim on 11/8/2020.
 */
class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val url = originalUrl.newBuilder()
            .addQueryParameter("openWeatherMap_api_key", BuildConfig.openWeatherMap_api_key)
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}