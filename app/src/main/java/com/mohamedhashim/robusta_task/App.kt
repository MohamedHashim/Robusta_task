package com.mohamedhashim.robusta_task

import android.app.Application
import com.mohamedhashim.robusta_task.di.networkModule
import com.mohamedhashim.robusta_task.di.repositoryModule
import com.mohamedhashim.robusta_task.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Mohamed Hashim on 11/8/2020.
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(networkModule)
            modules(viewModelModule)
            modules(repositoryModule)
        }
    }
}
