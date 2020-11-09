package com.mohamedhashim.robusta_task.ui.imageviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohamedhashim.robusta_task.base.LiveCoroutinesViewModel
import com.mohamedhashim.robusta_task.data.repository.WeatherRepository
import com.mohamedhashim.robusta_task.data.response.WeatherResponse

/**
 * Created by Mohamed Hashim on 11/8/2020.
 */
class ImageViewerViewModel constructor(
    private val weatherRepository: WeatherRepository
) : LiveCoroutinesViewModel() {

    var weatherLiveData: LiveData<WeatherResponse>
    val toastLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        this.weatherLiveData =
            launchOnViewModelScope {
                this.weatherRepository.loadWeathers { this.toastLiveData.postValue(it) }
            }
    }
}