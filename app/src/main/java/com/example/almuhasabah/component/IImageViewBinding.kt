package com.example.almuhasabah.component

import android.widget.ImageView
import androidx.databinding.BindingAdapter

interface IImageViewBinding {

    @BindingAdapter("customImageFromDrawable")
    fun setImageFromDrawable(imageView: ImageView, filePath: Int?)

    @BindingAdapter("customImageFromUrl")
    fun setImageFromUrl(imageView: ImageView, filePath: String?)
}