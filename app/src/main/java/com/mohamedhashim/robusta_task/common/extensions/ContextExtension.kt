package com.mohamedhashim.robusta_task.common.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.mohamedhashim.robusta_task.R
import com.mohamedhashim.robusta_task.ui.fragments.CameraFragment.Companion.REQUIRED_PERMISSIONS
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * Created by Mohamed Hashim on 11/8/2020.
 */
fun Context.toast(message: String) =
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

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

fun initToolbar(toolbar: Toolbar, activity: AppCompatActivity) {
    activity.setSupportActionBar(toolbar)
    activity.supportActionBar?.title = activity.getString(R.string.robusta)
}

fun saveWeatherPhoto(activity: Activity, bitmap: Bitmap, photoPath: String) {
    var fOut: OutputStream? = null
    val file = File(photoPath)
    fOut = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut)
    fOut.flush()
    fOut.close()
    MediaStore.Images.Media.insertImage(
            activity!!.contentResolver,
            file.absolutePath,
            file.name,
            file.name
    )
}

fun shareWeatherPhoto(context: Context, photoPath: String) {
    try {
        val myFile: File = File(photoPath)
        val mime = MimeTypeMap.getSingleton()
        val ext = myFile.name.substring(myFile.name.lastIndexOf(".") + 1)
        val type = mime.getMimeTypeFromExtension(ext)
        val sharingIntent = Intent("android.intent.action.SEND")
        sharingIntent.type = type
        sharingIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(myFile))
        context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.share_title)))
    } catch (e: Exception) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    }
}