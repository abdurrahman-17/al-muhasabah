package com.example.almuhasabah

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.example.almuhasabah.component.BaseDataBindingComponent

class AlMuhasabah : Application() {

    companion object{
        private var mInstance : AlMuhasabah? = null

        fun getInstance() : AlMuhasabah? {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        DataBindingUtil.setDefaultComponent(BaseDataBindingComponent())
    }

}