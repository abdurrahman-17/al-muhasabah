package com.example.almuhasabah.ui.homefragment

import android.app.AlertDialog
import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.almuhasabah.base.BaseViewModel
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.data.repository.BaseResponse
import com.example.almuhasabah.data.repository.LoginControllerRepository
import com.example.almuhasabah.model.emailvalidation.hadeesimageslider.HadeesImageSliderDataItem
import com.example.almuhasabah.model.emailvalidation.hadesslist.HadeesListItem
import com.example.almuhasabah.utils.Resource
import com.example.almuhasabah.utils.SingleLiveData
import javax.inject.Inject

class HomeFragmentViewModel@Inject constructor(application: Application) : BaseViewModel(application) {



    val mShowProgressBar= SingleLiveData<Boolean>()
    var sharedPreferences : SharedPreferenceImp?= null

    val name = ObservableField<String>("")
    val image = ObservableField<String>("")
    val api = ObservableField("")

    val hadeesHeaderListData = MutableLiveData<Resource<BaseResponse<List<HadeesListItem>>>>()
    var hadeesHeaderList = ObservableArrayList<HadeesListItem>()

    val hadeesImageSliderListData = MutableLiveData<Resource<BaseResponse<List<HadeesImageSliderDataItem>>>>()
   // var hadeesImageSliderList = ObservableArrayList<HadeesImageSliderDataItem>()
    var hadeesImageSliderList = ArrayList<HadeesImageSliderDataItem>()


    init {
        sharedPreferences = SharedPreferenceImp()
        hadeesHeaderListApi()
        hadeesImageSliderListApi()
    }

    private fun hadeesImageSliderListApi() {
        mShowProgressBar.value = true
        LoginControllerRepository.getInstance().hadeesImageSliderListApi(hadeesImageSliderListData)
    }

    private fun hadeesHeaderListApi() {
        mShowProgressBar.value = true
        LoginControllerRepository.getInstance().hadeesHeaderListApi(hadeesHeaderListData)
    }


    fun press(view:View){
        val builder = AlertDialog.Builder(getApplication())
        //set title for alert dialog
        builder.setTitle("hii")
        //set message for alert dialog
        builder.setMessage("this is me")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            Toast.makeText(getApplication(),"clicked yes",Toast.LENGTH_LONG).show()
        }
        //performing cancel action
        builder.setNeutralButton("Cancel"){dialogInterface , which ->
            Toast.makeText(getApplication(),"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
        }
        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->
            Toast.makeText(getApplication(),"clicked No",Toast.LENGTH_LONG).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }



}

