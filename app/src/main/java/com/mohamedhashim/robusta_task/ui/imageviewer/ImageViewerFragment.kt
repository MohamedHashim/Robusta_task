package com.mohamedhashim.robusta_task.ui.imageviewer

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.mohamedhashim.robusta_task.R
import com.mohamedhashim.robusta_task.base.DataBindingFragment
import com.mohamedhashim.robusta_task.databinding.FragmentImageViewerBinding
import com.mohamedhashim.robusta_task.ui.camera.CameraFragment
import kotlinx.android.synthetic.main.fragment_image_viewer.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


/**
 * Created by Mohamed Hashim on 11/9/2020.
 */
class ImageViewerFragment : DataBindingFragment() {
    lateinit var photoPath: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        photoPath = requireArguments().get(CameraFragment.photoPathKey).toString()
        return binding<FragmentImageViewerBinding>(
            inflater, R.layout.fragment_image_viewer, container
        ).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val savedUri = Uri.fromFile(File(photoPath))

        val capturedPhoto = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, savedUri)

        val bannerDrawable = resources.getDrawable(R.drawable.banner)
        val bannerBitmap = drawableToBitmap(bannerDrawable)
        val fullBitmap = drawTextToBitmap(activity!!, bannerBitmap!!, "Robusta !")
        val res = drawTextToBitmap(activity!!, bannerBitmap, "")

        val scaledBanner = Bitmap.createScaledBitmap(
            fullBitmap!!,
            capturedPhoto.width,
            capturedPhoto.height / 4,
            false
        )


        val final = drawBitmapToBitmap(activity!!, capturedPhoto, scaledBanner)
        imageView.setImageBitmap(final)
        saveWeatherPhoto(final!!)
        share.setOnClickListener {
            shareWeatherPhoto()
        }
    }

    private fun saveWeatherPhoto(bitmap: Bitmap) {
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

    private fun shareWeatherPhoto() {
        try {
            val myFile: File = File(photoPath)
            val mime = MimeTypeMap.getSingleton()
            val ext = myFile.name.substring(myFile.name.lastIndexOf(".") + 1)
            val type = mime.getMimeTypeFromExtension(ext)
            val sharingIntent = Intent("android.intent.action.SEND")
            sharingIntent.type = type
            sharingIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(myFile))
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_title)))
        } catch (e: Exception) {
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun drawBitmapToBitmap(
        gContext: Context,
        bmp: Bitmap,
        b: Bitmap
    ): Bitmap? {
        val resources: Resources = gContext.resources
        val scale: Float = resources.displayMetrics.density
        var bitmap = bmp
        var bitmapConfig = bitmap.config
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true)
        val canvas = Canvas(bitmap)

        // draw text banner bitmap to the Canvas center
        canvas.drawBitmap(b, 0f, 0f, null)

        return bitmap
    }


    // Add photo above the other
    fun combineImages(c: Bitmap?, s: Bitmap?): Bitmap {
        var cs: Bitmap? = null
        val metrics: DisplayMetrics = this.resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val comboImage = Canvas(cs)
        val dest1 = Rect(0, 0, width, height) // left,top,right,bottom
        comboImage.drawBitmap(c!!, null, dest1, null)
        val dest2 = Rect(0, height - 1000, width, height)
        comboImage.drawBitmap(s!!, null, dest2, null)
        return cs
    }


    //////////////////////////////////////////////////////////////////////////////////
    private fun drawableToBitmap(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val bitmap =
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.alpha = 190
        drawable.draw(canvas)
        return bitmap
    }


    private fun drawTextToBitmap(
        gContext: Context,
        bmp: Bitmap,
        gText: String
    ): Bitmap? {
        val resources: Resources = gContext.resources
        val scale: Float = resources.displayMetrics.density
        var bitmap = bmp
        var bitmapConfig = bitmap.config
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true)
        val canvas = Canvas(bitmap)
        // new antialised Paint
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        // text color - #3D3D3D
        paint.color = Color.rgb(61, 61, 61)
        // text size in pixels
        paint.textSize = (30 * scale).toInt().toFloat()
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.BLACK)

        // draw text to the Canvas center
        val bounds = Rect()
        paint.getTextBounds(gText, 0, gText.length, bounds)
        val x = (bitmap.width - bounds.width()) / 2
        val y = (bitmap.height + bounds.height()) / 2
        canvas.drawText(gText, x.toFloat(), y.toFloat(), paint)
//        canvas.drawText("second text", x.toFloat(), y.toFloat()+100, paint)
//        canvas.drawText("third text", x.toFloat(), y.toFloat()+200, paint)
        return bitmap
    }

}