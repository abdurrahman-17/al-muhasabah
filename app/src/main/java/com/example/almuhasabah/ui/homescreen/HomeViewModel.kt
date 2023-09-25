package com.example.almuhasabah.ui.homescreen

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import com.example.almuhasabah.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel@Inject constructor(application: Application) : BaseViewModel(application) {

    fun onClickAction(view: View?){

    }


    val api =ObservableField("")
}