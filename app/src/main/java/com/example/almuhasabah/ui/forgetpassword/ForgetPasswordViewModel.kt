package com.example.almuhasabah.ui.forgetpassword

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.almuhasabah.base.BaseViewModel
import com.example.almuhasabah.data.repository.BaseResponse
import com.example.almuhasabah.data.repository.LoginControllerRepository
import com.example.almuhasabah.model.emailvalidation.forgotpassword.ForgotPasswordDataItem
import com.example.almuhasabah.model.emailvalidation.otpvalidation.SignupOtpDataItem
import com.example.almuhasabah.utils.Resource
import com.example.almuhasabah.utils.SingleLiveData
import javax.inject.Inject

class ForgetPasswordViewModel@Inject constructor(application: Application) : BaseViewModel(application)  {

    val mShowProgressBar= SingleLiveData<Boolean>()
    val forgetPassDataResponse= MutableLiveData<Resource<BaseResponse<ForgotPasswordDataItem>>>()

    val email= ObservableField<String>("")


    fun forgetPasswordApi(){
        mShowProgressBar.value=true
        LoginControllerRepository.getInstance().forgotPassword(forgetPassDataResponse,email.get().toString())
    }
}