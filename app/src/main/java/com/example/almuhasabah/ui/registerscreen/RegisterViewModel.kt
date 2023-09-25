package com.example.almuhasabah.ui.registerscreen

import android.app.Application
import android.view.View
import android.widget.AdapterView
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.almuhasabah.base.BaseViewModel
import com.example.almuhasabah.data.repository.BaseResponse
import com.example.almuhasabah.data.repository.LoginControllerRepository
import com.example.almuhasabah.model.emailvalidation.registersignup.SignUpDataItem
import com.example.almuhasabah.utils.Resource
import com.example.almuhasabah.utils.SingleLiveData
import javax.inject.Inject

class RegisterViewModel@Inject constructor(application: Application) : BaseViewModel(application) {


    val mShowProgressBar = SingleLiveData<Boolean>()
    val registerDataResponse = MutableLiveData<Resource<BaseResponse<SignUpDataItem>>>()

    val name= ObservableField<String>("")
    val email= ObservableField<String>("")
    val password= ObservableField<String>("")
    val role_id= ObservableField<String>("2")
    val age= ObservableField<String>("")
    val gender= ObservableField<String>("")
    val marital_status= ObservableField<String>("")
    val username= ObservableField<String>("")
    val phone_no= ObservableField<String>("")
    var mGetGenderObserver = ObservableField<String>("")
    var mGetIdObserver = ObservableField<String>("")

    val ageNumber= ObservableField<String>("")





    fun onSelectGender(
        parent: AdapterView<*>?,
        view: View?,
        pos: Int,
        id: Long
    ){
        if (pos!=0){
            gender.set(parent?.selectedItem.toString())
        }
        mGetGenderObserver.set(parent?.getItemAtPosition(pos).toString())
    }


    fun onSelectedStatus(
        parent: AdapterView<*>?,
        view: View?,
        pos: Int,
        id: Long
    ){
        if (pos!=0){
            marital_status.set(parent?.selectedItem.toString())
        }
        mGetIdObserver.set(parent?.getItemAtPosition(pos).toString())
    }

    fun registerApi(){
        mShowProgressBar.value=true
        LoginControllerRepository.getInstance().register(registerDataResponse,name.get().toString(),email.get().toString(),password.get().toString(),
            role_id.get().toString(),age.get().toString(),gender.get().toString(),marital_status.get().toString(),username.get().toString(),phone_no.get().toString(),)
    }
}