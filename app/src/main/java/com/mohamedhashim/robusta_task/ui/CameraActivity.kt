package com.mohamedhashim.robusta_task.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.mohamedhashim.robusta_task.R
import com.mohamedhashim.robusta_task.common.extensions.allPermissionsGranted
import com.mohamedhashim.robusta_task.common.extensions.toast
import com.mohamedhashim.robusta_task.ui.fragments.CameraFragment

class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults:
            IntArray
    ) {
        if (requestCode == CameraFragment.REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted(this)) {
                //navigate to CameraFragment
                findNavController(R.id.camera_fragment).navigate(R.id.action_splashFragment_to_cameraFragment)
                toast(getString(R.string.permission_granted))
            } else {
                toast(getString(R.string.permission_not_granted))
                finish()
            }
        }
    }

    override fun onSupportNavigateUp() =
            Navigation.findNavController(this, R.id.cameraFragment).navigateUp()
}