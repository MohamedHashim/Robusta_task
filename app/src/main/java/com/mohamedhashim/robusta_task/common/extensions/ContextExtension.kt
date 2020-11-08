package com.mohamedhashim.robusta_task.common.extensions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.mohamedhashim.robusta_task.R
import com.mohamedhashim.robusta_task.ui.camera.CameraFragment.Companion.REQUIRED_PERMISSIONS
import java.io.File

/**
 * Created by Mohamed Hashim on 11/8/2020.
 */
fun allPermissionsGranted(baseContext: Activity) = REQUIRED_PERMISSIONS.all {
    ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
}

fun getOutputDirectory(activity: Activity): File {
    val mediaDir = activity.externalMediaDirs.firstOrNull()?.let {
        File(it, activity.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else activity.filesDir
}
