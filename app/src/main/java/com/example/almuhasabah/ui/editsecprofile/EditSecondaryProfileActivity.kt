package com.example.almuhasabah.ui.editsecprofile

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isEmpty
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseActivity
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.databinding.ActivityEditSecondaryProfileBinding
import com.example.almuhasabah.ui.homescreen.HomeActivity
import com.example.almuhasabah.utils.Constants
import kotlinx.android.synthetic.main.activity_edit_secondary_profile.*
import java.util.*

class EditSecondaryProfileActivity : BaseActivity<ActivityEditSecondaryProfileBinding,EditSecondaryProfileViewModel>() {

    val editSecProfileVM:EditSecondaryProfileViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.editSecProfileVM
    override val layoutId: Int
        get() = R.layout.activity_edit_secondary_profile
    override val viewModel: EditSecondaryProfileViewModel
        get() {
            return editSecProfileVM
        }

    var sharedPreferences : SharedPreferenceImp? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = SharedPreferenceImp()

        viewModel.sec_user_id.set(sharedPreferences?.getString(Constants.SEC_ID))
        viewModel.name.set(sharedPreferences?.getString(Constants.SEC_NAME))
        viewModel.phone_no.set(sharedPreferences?.getString(Constants.SEC_PHONE))
        viewModel.ageNo.set(sharedPreferences?.getString(Constants.SEC_AGE))
        viewModel.gender.set(sharedPreferences?.getString(Constants.SEC_GENDER))
        viewModel.marital_status.set(sharedPreferences?.getString(Constants.SEC_STATUS))


        viewModel.mShowProgressBar.observeEvent(this, androidx.lifecycle.Observer {
            it.let {
                showProgressBar(edit_sec_pro)
            }
        })

        viewDataBinding?.submitBtn?.setOnClickListener {

            if(editProfileValidation()){
                viewModel.editSecProfileApi()
            }
        }

        viewDataBinding?.calenderPicker?.setOnClickListener {

            val calendar= Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)

            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
            { view, year, monthOfYear, dayOfMonth ->

                val age = currentYear - year
                val date = "" + dayOfMonth + "/" + (monthOfYear+1) + "/" + year
                viewDataBinding?.AgeEt?.setText(date)
                viewModel.age.set(age.toString())
            }, year, month, day)

            datePickerDialog.show()
        }


        viewModel.editSecProfileDataResponse.observe(this, androidx.lifecycle.Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true->
                    {
                        putToast(it.data.message)
                        val i = Intent(this, HomeActivity::class.java)
                        startActivity(i)

                    }
                    false->
                    {
                        putToast(it.data.message)
                    }
                    else->
                    {
                        putToast("Internet Issue")
                    }
                }

            }

        })





    }


    private fun editProfileValidation(): Boolean {
        when{

            viewDataBinding?.nameEt?.text.toString().isEmpty()->{
                viewDataBinding?.nameEt?.error="Please enter name"
                viewDataBinding?.nameEt?.requestFocus()
            }

//            viewDataBinding?.AgeEt?.text.toString().isEmpty()->{
//                viewDataBinding?.AgeEt?.error="Please enter age"
//                viewDataBinding?.AgeEt?.requestFocus()
//            }

            viewDataBinding?.genderSp?.isEmpty() == true ->{
                viewDataBinding?.genderSp?.requestFocus()
            }

            viewDataBinding?.maritalEt?.isEmpty()==true ->{
                viewDataBinding?.maritalEt?.requestFocus()
            }

            viewDataBinding?.contactEt?.text.toString().isEmpty()->{
                viewDataBinding?.contactEt?.error="Please enter phone number"
                viewDataBinding?.contactEt?.requestFocus()
            }

            else->return true
        }
        return false
    }



}