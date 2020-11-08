package com.mohamedhashim.robusta_task.ui.imageviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mohamedhashim.robusta_task.R
import com.mohamedhashim.robusta_task.base.DataBindingFragment
import com.mohamedhashim.robusta_task.databinding.FragmentImageViewerBinding

/**
 * Created by Mohamed Hashim on 11/9/2020.
 */
class ImageViewerFragment : DataBindingFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding<FragmentImageViewerBinding>(
            inflater, R.layout.fragment_image_viewer, container
        ).root
    }
}