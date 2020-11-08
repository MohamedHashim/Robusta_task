package com.mohamedhashim.robusta_task.di

import com.mohamedhashim.robusta_task.data.repository.WeatherRepository
import org.koin.dsl.module

/**
 * Created by Mohamed Hashim on 11/8/2020.
 */
val repositoryModule = module {
    single { WeatherRepository(get()) }
}