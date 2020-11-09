package com.mohamedhashim.robusta_task.ui.camera

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.mohamedhashim.robusta_task.R
import com.mohamedhashim.robusta_task.base.DataBindingFragment
import com.mohamedhashim.robusta_task.common.extensions.getOutputDirectory
import com.mohamedhashim.robusta_task.common.extensions.toast
import com.mohamedhashim.robusta_task.databinding.FragmentCameraBinding
import com.mohamedhashim.robusta_task.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_camera.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * Created by Mohamed Hashim on 11/8/2020.
 */
class CameraFragment : DataBindingFragment() {

    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding<FragmentCameraBinding>(
            inflater, R.layout.fragment_camera, container
        ).apply {
            viewModel = this@CameraFragment.viewModel
            lifecycleOwner = this@CameraFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
    }

    private fun initializeUI() {

        startCamera()
        // Set up the listener for take photo button
        //TODO make it in binding adapter
        camera_capture_button.setOnClickListener { takePhoto() }

        outputDirectory = getOutputDirectory(activity!!)

        cameraExecutor = Executors.newSingleThreadExecutor()
    }


    override fun onResume() {
        super.onResume()
        startCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(activity!!.baseContext)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.createSurfaceProvider())
                }

            imageCapture = ImageCapture.Builder()
                .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(activity))
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(activity),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    val msg = getString(R.string.capture_failed) + "${exc.message}"
                    Log.e(TAG, msg, exc)
                    activity!!.toast(msg)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = getString(R.string.capture_succeeded) + "$savedUri"
                    Log.i(TAG, msg)
                    findNavController().navigate(
                        R.id.action_cameraFragment_to_imageViewerFragment,
                        createArguments(savedUri.path.toString())
                    )
                }
            })
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        const val photoPathKey = "photoPathKey"
        const val REQUEST_CODE_PERMISSIONS = 10
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        fun createArguments(photoPath: String): Bundle {
            val bundle = Bundle()
            bundle.putString(photoPathKey, photoPath)
            return bundle
        }
    }
}