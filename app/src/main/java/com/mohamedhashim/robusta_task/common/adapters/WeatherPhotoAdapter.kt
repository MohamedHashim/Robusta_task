package com.mohamedhashim.robusta_task.common.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedhashim.robusta_task.R
import com.mohamedhashim.robusta_task.common.extensions.shareWeatherPhoto


/**
 * Created by Mohamed Hashim on 11/9/2020.
 */
//TODO Implement data binding for adapter to be generic
class WeatherPhotoAdapter(photos: List<String>) : RecyclerView.Adapter<WeatherPhotoAdapter.ViewHolder?>() {

    private var mPhotos: List<String>? = photos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context = parent.context
        val inflater = LayoutInflater.from(context)
        val weatherPhotoView: View = inflater.inflate(R.layout.item_weather_photo, parent, false)
        return ViewHolder(weatherPhotoView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: WeatherPhotoAdapter.ViewHolder, position: Int) {
        val photoPath = mPhotos!![position]
        val bitmap = BitmapFactory.decodeFile(photoPath)

        // Set item views based on your views and data model
        holder.img.setImageBitmap(bitmap)
        holder.share.setOnClickListener {
            shareWeatherPhoto(holder.itemView.context, photoPath)
        }
    }

    override fun getItemCount(): Int {
        return mPhotos!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.img)
        var share: ImageView = itemView.findViewById(R.id.share)
    }
}