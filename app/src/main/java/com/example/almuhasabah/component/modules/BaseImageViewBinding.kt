package com.example.almuhasabah.component.modules

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.almuhasabah.R
import com.example.almuhasabah.component.IImageViewBinding

class BaseImageViewBinding : IImageViewBinding {

    override fun setImageFromDrawable(imageView: ImageView, filePath: Int?) {
        Glide.with(imageView.context).load(filePath).into(imageView)
    }

    override fun setImageFromUrl(imageView: ImageView, filePath: String?) {
        val option: RequestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .fitCenter()

        Glide.with(imageView.context)
                .load(filePath)
                .apply(option)
                .centerCrop()
                .into(imageView)
    }
}