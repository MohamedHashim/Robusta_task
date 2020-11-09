//package com.mohamedhashim.robusta_task.common.adapters
//
//import android.view.View
//import com.mohamedhashim.robusta_task.R
//import com.mohamedhashim.robusta_task.data.entity.History
//import com.skydoves.baserecyclerviewadapter.BaseAdapter
//import com.skydoves.baserecyclerviewadapter.SectionRow
//
///**
// * Created by Mohamed Hashim on 11/9/2020.
// */
//class HistoryAdapter() : BaseAdapter() {
//
//    init {
//        addSection(ArrayList<History>())
//    }
//
//    fun addHistoryList(photoPaths: List<String>) {
//        val section = sections()[0]
//        section.addAll(photoPaths)
//        notifyItemRangeInserted(section.size - photoPaths.size + 1, photoPaths.size)
//    }
//
//    override fun layout(sectionRow: SectionRow) = R.layout.item_weather_photo2
//
//    override fun viewHolder(layout: Int, view: View) = HistoryViewHolder(view)
//}