package com.mohamedhashim.robusta_task.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Mohamed Hashim on 11/8/2020.
 */

@Parcelize
class Wind(
    val speed: Float,
    val deg: Int
) : Parcelable