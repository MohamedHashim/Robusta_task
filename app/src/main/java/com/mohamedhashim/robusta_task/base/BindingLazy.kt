//package com.mohamedhashim.robusta_task.base
//
//import android.view.View
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.ViewDataBinding
//
///**
// * Created by Mohamed Hashim on 11/8/2020.
// */
//inline fun <reified T : ViewDataBinding> bindings(view: View): Lazy<T> =
//    lazy {
//        DataBindingUtil.bind<T>(view)
//            ?: throw IllegalAccessException("cannot find the matched view to layout.")
//    }
