package com.mohamedhashim.robusta_task.ui.fragments

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohamedhashim.robusta_task.R
import com.mohamedhashim.robusta_task.base.DataBindingFragment
import com.mohamedhashim.robusta_task.common.adapters.WeatherPhotoAdapter
import com.mohamedhashim.robusta_task.databinding.FragmentHistoryBinding
import kotlinx.android.synthetic.main.fragment_history.*
import java.io.File

/**
 * Created by Mohamed Hashim on 11/9/2020.
 */
class HistoryFragment : DataBindingFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return binding<FragmentHistoryBinding>(
                inflater, R.layout.fragment_history, container
        ).apply {
            lifecycleOwner = this@HistoryFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        val path: String =
                Environment.getExternalStorageDirectory()
                        .toString() + base_path

        val list = createGridItems(path)!!
        if (list.isNotEmpty()) {
            iv_not_found.visibility = View.INVISIBLE
            tx_not_found.visibility = View.INVISIBLE
        }
        val adapter = WeatherPhotoAdapter(list)

        recyclerView.adapter = adapter


        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun createGridItems(directoryPath: String): List<String>? {
        val items: MutableList<String> = ArrayList()
        // List all the items within the folder.
        val files: Array<File> = File(directoryPath).listFiles()
        for (file in files) {
            items.add(file.absolutePath)
        }
        return items
    }

    companion object {
        private const val base_path = "/Android/media/com.mohamedhashim.robusta_task/Robusta_task"
    }
}