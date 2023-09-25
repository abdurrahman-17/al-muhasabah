package com.example.almuhasabah.ui.editprofile

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.view.View
import android.widget.AdapterView
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.almuhasabah.base.BaseViewModel
import com.example.almuhasabah.data.repository.BaseResponse
import com.example.almuhasabah.data.repository.LoginControllerRepository
import com.example.almuhasabah.model.emailvalidation.editprofile.EditProfileDataItem
import com.example.almuhasabah.model.emailvalidation.profilepicdataitem.ProfilePicDataItem
import com.example.almuhasabah.utils.Resource
import com.example.almuhasabah.utils.SingleLiveData
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject








class EditProfileViewModel@Inject constructor(application: Application) : BaseViewModel(application) {

    val mShowProgressBar= SingleLiveData<Boolean>()
    val editProfileDataResponse= MutableLiveData<Resource<BaseResponse<EditProfileDataItem>>>()

    val name=ObservableField<String>("")
    val age=ObservableField<String>("")
    val ageNo=ObservableField<String>("")
    val gender=ObservableField<String>("")
    val marital_status=ObservableField<String>("")
    val phone_no=ObservableField<String>("")
    var mGetGenderObserver = ObservableField<String>("")
    var mGetIdObserver = ObservableField<String>("")
    var apiToken = ObservableField<String>("")
    var sdImageMainDirectory: File? = null
    var outputFileUri: Uri? = null
    var resultUri: Uri? = null
    var currentPicture: String? = null
    var file : File? = null
    var bitmap: Bitmap? = null



    val profilePicDataResponse= MutableLiveData<Resource<BaseResponse<ProfilePicDataItem>>>()
    val profile_user_id=ObservableField<String>("")
    var profile =""
    //val profile:String=""
    //var image: String = bitmap?.let { convertToString(it).toString() }.toString()

    val pro =convertToString()!!
    var fileName = ""
    var completePath = Environment.getExternalStorageDirectory().toString() + "/" + fileName


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

    private fun convertToString(): String? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imgByte: ByteArray = byteArrayOutputStream.toByteArray()

        return android.util.Base64.encodeToString(imgByte, android.util.Base64.NO_WRAP )
    }


    fun editProfileApi(){
        mShowProgressBar.value=true
        LoginControllerRepository.getInstance().editProfile(
            editProfileDataResponse,
            name.get().toString(),
            age.get().toString(),
            gender.get().toString(),
            marital_status.get().toString(),
            phone_no.get().toString(),
        )
    }

    fun profilePicApi() {
        LoginControllerRepository.getInstance().profilePicApi(profilePicDataResponse,
            profile_user_id.get().toString(), profile)
    }

}