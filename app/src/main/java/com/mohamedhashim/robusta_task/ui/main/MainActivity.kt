package com.mohamedhashim.robusta_task.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mohamedhashim.robusta_task.R
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        observeMessages()

        this.viewModel.weatherLiveData.observe(this) {
            if (it != null)
                Toast.makeText(this, it.main.humidity.toString(), Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "NO DATA", Toast.LENGTH_LONG).show()

        }
    }

    private fun observeMessages() =
        this.viewModel.toastLiveData.observe(
            this,
            { Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show() })
}