package com.mohamedhashim.robusta_task.ui.imageviewer

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mohamedhashim.robusta_task.R
import com.mohamedhashim.robusta_task.base.DataBindingFragment
import com.mohamedhashim.robusta_task.databinding.FragmentImageViewerBinding
import com.mohamedhashim.robusta_task.ui.camera.CameraFragment
import kotlinx.android.synthetic.main.fragment_image_viewer.*
import java.io.File

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

        val bitmap =
            MediaStore.Images.Media.getBitmap(activity!!.contentResolver, savedUri)
        imageView.setImageBitmap(bitmap)
    }
}