package com.mohamedhashim.robusta_task.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mohamedhashim.robusta_task.R
import com.mohamedhashim.robusta_task.base.DataBindingFragment
import com.mohamedhashim.robusta_task.databinding.FragmentHistoryBinding

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
    }
}