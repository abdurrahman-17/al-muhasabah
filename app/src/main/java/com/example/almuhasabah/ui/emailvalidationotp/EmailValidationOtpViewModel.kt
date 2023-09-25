package com.example.almuhasabah.ui.emailvalidationotp

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.almuhasabah.base.BaseViewModel
import com.example.almuhasabah.data.repository.BaseResponse
import com.example.almuhasabah.data.repository.LoginControllerRepository
import com.example.almuhasabah.model.emailvalidation.otpvalidation.SignupOtpDataItem
import com.example.almuhasabah.utils.Resource
import com.example.almuhasabah.utils.SingleLiveData
import javax.inject.Inject

class EmailValidationOtpViewModel@Inject constructor(application: Application) : BaseViewModel(application) {

    val mShowProgressBar= SingleLiveData<Boolean>()
    val otpDataResponse= MutableLiveData<Resource<BaseResponse<SignupOtpDataItem>>>()

    val otp= ObservableField<String>("")
    val otpTrue= ObservableField<String>("")
    val email= ObservableField<String>("")


    fun otpValidationApi(){
        mShowProgressBar.value=true
        LoginControllerRepository.getInstance().otpVerify(otpDataResponse,email.get().toString(),otp.get().toString())
    }


}