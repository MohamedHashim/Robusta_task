package com.mohamedhashim.robusta_task.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mohamedhashim.robusta_task.R
import com.mohamedhashim.robusta_task.common.extensions.allPermissionsGranted
import com.mohamedhashim.robusta_task.ui.fragments.CameraFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Mohamed Hashim on 11/9/2020.
 */
class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // check camera permissions
        if (allPermissionsGranted(activity!!)) {
            GlobalScope.launch {
                delay(2000L)
                findNavController().navigate(R.id.action_splashFragment_to_cameraFragment)
            }
        } else {
            ActivityCompat.requestPermissions(
                activity!!,
                CameraFragment.REQUIRED_PERMISSIONS,
                CameraFragment.REQUEST_CODE_PERMISSIONS
            )
        }
    }
}
