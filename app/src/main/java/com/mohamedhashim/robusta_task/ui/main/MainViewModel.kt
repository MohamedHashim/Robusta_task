package com.mohamedhashim.robusta_task.ui.main

import android.graphics.Movie
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.mohamedhashim.robusta_task.adapters.LiveCoroutinesViewModel
import com.mohamedhashim.robusta_task.data.repository.WeatherRepository
import com.mohamedhashim.robusta_task.data.response.WeatherResponse

/**
 * Created by Mohamed Hashim on 11/8/2020.
 */
class MainViewModel constructor(
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

//    fun postMoviePage(page: Int) = this.moviePageLiveData.postValue(page)
//    fun getFavouriteMovieList() = this.moviesRepository.getFavouriteMoviesList()


}