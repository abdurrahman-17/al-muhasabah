package com.example.almuhasabah.component

import androidx.databinding.DataBindingComponent
import com.example.almuhasabah.component.modules.BaseImageViewBinding

class BaseDataBindingComponent : DataBindingComponent {

    override fun getIImageViewBinding(): IImageViewBinding {
        return BaseImageViewBinding()
    }
}