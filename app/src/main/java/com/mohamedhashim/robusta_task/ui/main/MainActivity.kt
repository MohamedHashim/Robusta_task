package com.mohamedhashim.robusta_task.ui.main

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mohamedhashim.robusta_task.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeMessages()

        this.viewModel.weatherLiveData.observe(this) {
            if (it != null)
                Toast.makeText(this, it.main.humidity.toString(), Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "NO DATA", Toast.LENGTH_LONG).show()

        }

        val bannerDrawable = resources.getDrawable(R.drawable.banner)
        val bannerBitmap = drawableToBitmap(bannerDrawable)
        val fullBitmap = drawTextToBitmap(this, bannerBitmap!!, "Robusta !")
        img.setImageBitmap(fullBitmap)

    }

    private fun observeMessages() =
        this.viewModel.toastLiveData.observe(
            this,
            { Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show() })

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
        drawable.draw(canvas)
        return bitmap
    }


    private fun drawTextToBitmap(
        gContext: Context,
        bmp: Bitmap,
        gText: String
    ): Bitmap? {
        val resources: Resources = gContext.getResources()
        val scale: Float = resources.getDisplayMetrics().density
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
        paint.setTextSize((50 * scale).toInt().toFloat())
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE)

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