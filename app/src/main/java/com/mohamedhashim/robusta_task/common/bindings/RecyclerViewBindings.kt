//package com.mohamedhashim.robusta_task.common.bindings
//
//import androidx.databinding.BindingAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.mohamedhashim.robusta_task.common.adapters.HistoryAdapter
//import com.mohamedhashim.robusta_task.data.entity.History
//import com.skydoves.baserecyclerviewadapter.BaseAdapter
//import com.skydoves.whatif.whatIfNotNullOrEmpty
//
///**
// * Created by Mohamed Hashim on 11/9/2020.
// */
//@BindingAdapter("adapter")
//fun bindAdapter(view: RecyclerView, baseAdapter: BaseAdapter) {
//    view.adapter = baseAdapter
//}
//
//@BindingAdapter("adapterPhotoList")
//fun bindAdapterPathList(view: RecyclerView, history: History) {
//    history.photoPaths.whatIfNotNullOrEmpty {
//        val adapter = view.adapter as? HistoryAdapter
//        adapter?.addHistoryList(it)
//    }
//}