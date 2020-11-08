package com.mohamedhashim.robusta_task.di

import com.mohamedhashim.robusta_task.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Mohamed Hashim on 11/8/2020.
 */
val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}