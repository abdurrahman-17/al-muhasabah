package com.example.almuhasabah.ui.loginscreen

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.almuhasabah.base.BaseViewModel
import com.example.almuhasabah.data.repository.BaseResponse
import com.example.almuhasabah.data.repository.LoginControllerRepository
import com.example.almuhasabah.model.emailvalidation.login.LoginDataItem
import com.example.almuhasabah.utils.Resource
import com.example.almuhasabah.utils.SingleLiveData
import javax.inject.Inject

class LoginViewModel@Inject constructor(application: Application) : BaseViewModel(application) {

    val mShowProgressBar=SingleLiveData<Boolean>()
    val loginDataResponse= MutableLiveData<Resource<BaseResponse<LoginDataItem>>>()
    val email_name= ObservableField<String>("")
    val password= ObservableField<String>("")
    val email= ObservableField<String>("")
    val name= ObservableField<String>("")

    fun loginApi(){
        mShowProgressBar.value=true
        LoginControllerRepository.getInstance().login(loginDataResponse,email_name.get().toString(),password.get().toString(),email.get().toString())
    }

    fun loginValidations(): Boolean {
       when{
           email_name.get()!!.isEmpty() ->{
               putToast("Enter your email")
           }
           password.get()!!.isEmpty() ->{
               putToast("Enter your password")
           }

           else->return true
       }
        return false
    }
}