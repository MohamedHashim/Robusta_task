package com.mohamedhashim.robusta_task.api.client

import com.mohamedhashim.robusta_task.api.ApiResponse
import com.mohamedhashim.robusta_task.api.service.WeatherService
import com.mohamedhashim.robusta_task.api.transform
import com.mohamedhashim.robusta_task.data.response.WeatherResponse

/**
 * Created by Mohamed Hashim on 11/8/2020.
 */
class WeatherClient(private val service: WeatherService) {

    fun fetchWeather(
        onResult: (response: ApiResponse<WeatherResponse>) -> Unit
    ) {
        this.service.fetchWeather("Cairo").transform(onResult)
    }
}