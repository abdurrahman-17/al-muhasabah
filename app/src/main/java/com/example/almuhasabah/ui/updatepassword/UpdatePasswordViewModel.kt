package com.example.almuhasabah.ui.updatepassword

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.almuhasabah.base.BaseViewModel
import com.example.almuhasabah.data.repository.BaseResponse
import com.example.almuhasabah.data.repository.LoginControllerRepository
import com.example.almuhasabah.model.emailvalidation.updatepassword.UpdatePasswordDataItem
import com.example.almuhasabah.utils.Resource
import com.example.almuhasabah.utils.SingleLiveData
import javax.inject.Inject

class UpdatePasswordViewModel@Inject constructor(application: Application) : BaseViewModel(application) {

    val mShowProgressBar=SingleLiveData<Boolean>()
    val updatePasswordDataResponse=MutableLiveData<Resource<BaseResponse<UpdatePasswordDataItem>>>()

    val email= ObservableField<String>("")
    val otp= ObservableField<String>("")
    val otpTrue= ObservableField<String>("")
    val password= ObservableField<String>("")


    fun updatePasswordApi(){
        mShowProgressBar.value=true
        LoginControllerRepository.getInstance().updatePassword(updatePasswordDataResponse,
        email.get().toString(),otp.get().toString(),password.get().toString())
    }
}