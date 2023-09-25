package com.example.almuhasabah.ui.emailvalidation

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.almuhasabah.base.BaseViewModel
import com.example.almuhasabah.data.repository.BaseResponse
import com.example.almuhasabah.data.repository.LoginControllerRepository
import com.example.almuhasabah.model.emailvalidation.emailvalidation.SignUpEmailDataItem
import com.example.almuhasabah.utils.Resource
import com.example.almuhasabah.utils.SingleLiveData
import javax.inject.Inject

class EmailValidationViewModel@Inject constructor(application: Application) : BaseViewModel(application) {

    val mShowProgressBar=SingleLiveData<Boolean>()
    val emailDataResponse=MutableLiveData<Resource<BaseResponse<SignUpEmailDataItem>>>()

    val email=ObservableField<String>("")

    fun emailValidationApi(){
        mShowProgressBar.value=true
        LoginControllerRepository.getInstance().emailVerify(emailDataResponse,email.get().toString())
    }


}