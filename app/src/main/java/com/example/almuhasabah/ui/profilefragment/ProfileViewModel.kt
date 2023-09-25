package com.example.almuhasabah.ui.profilefragment

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.almuhasabah.base.BaseViewModel
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.data.repository.BaseResponse
import com.example.almuhasabah.data.repository.LoginControllerRepository
import com.example.almuhasabah.model.emailvalidation.getsecondaryprofile.GetSecondaryProfile
import com.example.almuhasabah.utils.Resource
import com.example.almuhasabah.utils.SingleLiveData
import javax.inject.Inject

class ProfileViewModel@Inject constructor(application: Application) : BaseViewModel(application)  {

    val mShowProgressBar=SingleLiveData<Boolean>()
    var sharedPreferences : SharedPreferenceImp?= null

    val name =ObservableField<String>("")
    val email =ObservableField<String>("")
    val profile =ObservableField<String>("")
    val api_token =ObservableField<String>("")

    val secname =ObservableField<String>("")
    val secemail =ObservableField<String>("")
    val secprofile =ObservableField<String>("")
    val secapi_token =ObservableField<String>("")

    val secondaryProfileListData = MutableLiveData<Resource<BaseResponse<List<GetSecondaryProfile>>>>()
    var secondaryProfileList = ObservableArrayList<GetSecondaryProfile>()



    init {
        sharedPreferences = SharedPreferenceImp()
        secondaryProfileListApi()
    }

    private fun secondaryProfileListApi() {
        mShowProgressBar.value = true
        LoginControllerRepository.getInstance().getSecondaryProfileListApi(secondaryProfileListData)
    }
}