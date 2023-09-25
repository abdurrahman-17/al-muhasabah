package com.example.almuhasabah.ui.secondaryuser

import android.app.Application
import android.view.View
import android.widget.AdapterView
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.almuhasabah.base.BaseViewModel
import com.example.almuhasabah.data.repository.BaseResponse
import com.example.almuhasabah.data.repository.LoginControllerRepository
import com.example.almuhasabah.model.emailvalidation.secondaryprofile.SecondaryProfileDataItem
import com.example.almuhasabah.utils.Resource
import com.example.almuhasabah.utils.SingleLiveData
import javax.inject.Inject

class SecondaryUserViewModel@Inject constructor(application: Application) : BaseViewModel(application) {

    val mShowProgressBar = SingleLiveData<Boolean>()
    val secondaryProfileDataResponse = MutableLiveData<Resource<BaseResponse<SecondaryProfileDataItem>>>()

    val name= ObservableField<String>("")
    val email= ObservableField<String>("")
    val password= ObservableField<String>("")
    val role_id= ObservableField<String>("3")
    val age= ObservableField<String>("")
    val gender= ObservableField<String>("")
    val marital_status= ObservableField<String>("")
    val username= ObservableField<String>("")
    val phone_no= ObservableField<String>("")
    val profile= ObservableField<String>("")
    var mGetGenderObserver = ObservableField<String>("")
    var mGetIdObserver = ObservableField<String>("")
    val parent_id= ObservableField<String>("")


    val secondaryProfileListData = MutableLiveData<Resource<BaseResponse<List<SecondaryProfileDataItem>>>>()
    var secondaryProfileList = ObservableArrayList<SecondaryProfileDataItem>()



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

    fun secondaryProfileApi(){
        LoginControllerRepository.getInstance().secondaryProfile(secondaryProfileDataResponse,
            name.get().toString(),email.get().toString(),password.get().toString(),
            role_id.get().toString(),age.get().toString(),gender.get().toString(),marital_status.get().toString(),username.get().toString(),phone_no.get().toString(),parent_id.get().toString())
    }
}