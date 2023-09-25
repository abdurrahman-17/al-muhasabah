package com.example.almuhasabah.ui.editsecprofile

import android.app.Application
import android.view.View
import android.widget.AdapterView
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.almuhasabah.base.BaseViewModel
import com.example.almuhasabah.data.repository.BaseResponse
import com.example.almuhasabah.data.repository.LoginControllerRepository
import com.example.almuhasabah.model.emailvalidation.secprofileupdatedataitem.SecondaryProfileUpdateDataItem
import com.example.almuhasabah.utils.Resource
import com.example.almuhasabah.utils.SingleLiveData
import javax.inject.Inject

class EditSecondaryProfileViewModel@Inject constructor(application: Application) : BaseViewModel(application) {

    val mShowProgressBar= SingleLiveData<Boolean>()
    val editSecProfileDataResponse= MutableLiveData<Resource<BaseResponse<SecondaryProfileUpdateDataItem>>>()

    var sec_user_id =ObservableField<String>("")
    var name =ObservableField<String>("")
    var ageNo =ObservableField<String>("")
    var age =ObservableField<String>("")
    val gender=ObservableField<String>("")
    val marital_status=ObservableField<String>("")
    val phone_no=ObservableField<String>("")
    var mGetGenderObserver = ObservableField<String>("")
    var mGetIdObserver = ObservableField<String>("")


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

    fun editSecProfileApi(){
        LoginControllerRepository.getInstance().editSecProfile(editSecProfileDataResponse,sec_user_id.get().toString(),age.get().toString(),
        gender.get().toString(),marital_status.get().toString(), profile = toString())
    }
}