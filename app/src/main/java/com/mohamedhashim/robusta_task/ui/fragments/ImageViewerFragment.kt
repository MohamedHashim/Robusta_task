package com.mohamedhashim.robusta_task.ui.fragments

import android.content.Context
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
import com.mohamedhashim.robusta_task.R
import com.mohamedhashim.robusta_task.base.DataBindingFragment
import com.mohamedhashim.robusta_task.common.extensions.saveWeatherPhoto
import com.mohamedhashim.robusta_task.common.extensions.shareWeatherPhoto
import com.mohamedhashim.robusta_task.common.extensions.toast
import com.mohamedhashim.robusta_task.data.response.WeatherResponse
import com.mohamedhashim.robusta_task.databinding.FragmentImageViewerBinding
import com.mohamedhashim.robusta_task.ui.viewmodels.ImageViewerViewModel
import kotlinx.android.synthetic.main.fragment_image_viewer.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File


/**
 * Created by Mohamed Hashim on 11/9/2020.
 */
class ImageViewerFragment : DataBindingFragment() {

    private lateinit var photoPath: String
    private val viewModel: ImageViewerViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        photoPath = requireArguments().get(CameraFragment.photoPathKey).toString()
        return binding<FragmentImageViewerBinding>(
                inflater, R.layout.fragment_image_viewer, container
        ).apply {
            viewModel = this@ImageViewerFragment.viewModel
            lifecycleOwner = this@ImageViewerFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMessages()

        val savedUri = Uri.fromFile(File(photoPath))

        val capturedPhoto = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, savedUri)


        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            if (it != null) {

                val bannerDrawable = resources.getDrawable(R.drawable.banner)
                val bannerBitmap = drawableToBitmap(bannerDrawable)
                val fullBitmap = drawTextToBitmap(activity!!, bannerBitmap!!, it)

                val scaledBanner = Bitmap.createScaledBitmap(
                        fullBitmap!!,
                        capturedPhoto.width,
                        capturedPhoto.height / 4,
                        false
                )

                val final = drawBitmapToBitmap(activity!!, capturedPhoto, scaledBanner)
                imageView.setImageBitmap(final)
                saveWeatherPhoto(activity!!, final!!, photoPath)
                share.setOnClickListener {
                    shareWeatherPhoto(activity!!, photoPath)
                }
            }
        }
    }

    private fun observeMessages() =
            this.viewModel.toastLiveData.observe(viewLifecycleOwner, { context?.toast(it.toString()) })


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
            data: WeatherResponse
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

        paint.color = Color.rgb(255, 255, 255)
        // text size in pixels
        paint.textSize = (30 * scale).toInt().toFloat()
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.BLACK)

        // draw text to the Canvas center
        val bounds = Rect()
//        paint.getTextBounds(gText, 0, gText.length, bounds)
        val x = (bitmap.width - bounds.width()) / 2
        val y = (bitmap.height + bounds.height()) / 3
        canvas.drawText(data.sys!!.country + ", " + data.name, 10f, y.toFloat(), paint)
        canvas.drawText(data.weather[0].description, 10f, y.toFloat() + 120, paint)
        canvas.drawText(data.main!!.temp.toString() + " C", 10f, y.toFloat() + 220, paint)
        canvas.drawText("Humidity " + data.main.humidity, 10f, y.toFloat() + 320, paint)
        canvas.drawText("Pressure " + data.main.pressure, 10f, y.toFloat() + 420, paint)
        return bitmap
    }

}