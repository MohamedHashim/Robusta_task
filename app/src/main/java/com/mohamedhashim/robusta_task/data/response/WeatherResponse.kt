package com.mohamedhashim.robusta_task.data.response

import com.mohamedhashim.robusta_task.data.entity.Main
import com.mohamedhashim.robusta_task.data.entity.Sys
import com.mohamedhashim.robusta_task.data.entity.Weather
import com.mohamedhashim.robusta_task.data.entity.Wind

/**
 * Created by Mohamed Hashim on 11/8/2020.
 */
class WeatherResponse(
    val weather: List<Weather>,
    val main: Main?,
    val wind: Wind?,
    val name: String,
    val sys: Sys?
)