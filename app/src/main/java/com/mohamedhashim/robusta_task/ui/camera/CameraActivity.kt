package com.mohamedhashim.robusta_task.ui.camera

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.mohamedhashim.robusta_task.R
import com.mohamedhashim.robusta_task.common.extensions.allPermissionsGranted

class CameraActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_camera)
        // Request camera permissions
        if (allPermissionsGranted(this)) {
            //TODO navigate to CameraFragment
//            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, CameraFragment.REQUIRED_PERMISSIONS, CameraFragment.REQUEST_CODE_PERMISSIONS
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == CameraFragment.REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted(this)) {
                //TODO navigate to CameraFragment
//                startCamera()
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }
}