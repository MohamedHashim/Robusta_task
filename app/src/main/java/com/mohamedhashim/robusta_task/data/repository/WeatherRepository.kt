package com.mohamedhashim.robusta_task.data.repository

import androidx.lifecycle.MutableLiveData
import com.mohamedhashim.robusta_task.api.ApiResponse
import com.mohamedhashim.robusta_task.api.client.WeatherClient
import com.mohamedhashim.robusta_task.api.message
import com.mohamedhashim.robusta_task.data.response.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Mohamed Hashim on 11/8/2020.
 */
class WeatherRepository constructor(
    private val weatherClient: WeatherClient
) {
    suspend fun loadWeathers(error: (String) -> Unit) =
        withContext(Dispatchers.IO) {
            val liveData = MutableLiveData<WeatherResponse>()
            var weatherResponse: WeatherResponse? = null

            weatherClient.fetchWeather { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        response.data.let { data ->
                            weatherResponse = data
                            liveData.apply { postValue(weatherResponse) }
                        }
                    }
                    is ApiResponse.Failure.Error -> error(response.message())
                    is ApiResponse.Failure.Exception -> error(response.message())
                }
            }
            liveData.apply { postValue(weatherResponse) }
        }
}