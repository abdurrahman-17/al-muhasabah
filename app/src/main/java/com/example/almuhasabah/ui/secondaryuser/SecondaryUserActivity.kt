package com.example.almuhasabah.ui.secondaryuser

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.adapter.BaseRecyclerViewAdapter
import com.example.almuhasabah.base.BaseActivity
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.databinding.ActivitySecondaryUserBinding
import com.example.almuhasabah.databinding.SecondaryUserListBinding
import com.example.almuhasabah.model.emailvalidation.secondaryprofile.SecondaryProfileDataItem
import com.example.almuhasabah.ui.homescreen.HomeActivity
import com.example.almuhasabah.utils.Constants
import kotlinx.android.synthetic.main.activity_secondary_user.*
import java.util.*

class SecondaryUserActivity : BaseActivity<ActivitySecondaryUserBinding,SecondaryUserViewModel>() {

    private val secondaryUserVM:SecondaryUserViewModel by viewModels()
    private var secondaryProfileAdapter : BaseRecyclerViewAdapter<SecondaryProfileDataItem,SecondaryUserListBinding>? = null
    var sharedPreferences : SharedPreferenceImp? = null


    override val bindingVariable: Int
        get() = BR.secondaryUserVM
    override val layoutId: Int
        get() = R.layout.activity_secondary_user
    override val viewModel: SecondaryUserViewModel
        get() {
            return secondaryUserVM
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = SharedPreferenceImp()

//        secondaryProfileAdapter = BaseRecyclerViewAdapter(R.layout.secondary_user_list,BR.secondaryProfileAdapter,
//        viewModel.secondaryProfileList,null,object:OnDataBindCallback<SecondaryUserListBinding>{
//                override fun onItemClick(view: View, position: Int, v: SecondaryUserListBinding) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onDataBind(
//                    v: SecondaryUserListBinding,
//                    onClickListener: View.OnClickListener
//                ) {
//                    TODO("Not yet implemented")
//                }
//
//            })

        viewModel.parent_id.set(sharedPreferences?.getString(Constants.ID))


        secondaryUserVM.mShowProgressBar.observeEvent(this, Observer {
            it.let {
                showProgressBar(secondary_profile_layout)
            }
        })

        viewDataBinding?.submitBtn?.setOnClickListener {

            if(secondaryProfileValidation()){
                secondaryUserVM.secondaryProfileApi()
            }
        }

        secondaryUserVM.secondaryProfileDataResponse.observe(this, Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true->
                    {
                        putToast(it.data.message)
                        val i= Intent(this, HomeActivity::class.java)
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
                viewDataBinding?.ageEt?.setText(date)
                viewModel.age.set(age.toString())
            }, year, month, day)

            datePickerDialog.show()
        }

    }

    private fun secondaryProfileValidation(): Boolean {

        when{

            viewDataBinding?.nameEt?.text.toString().isEmpty()->{
                viewDataBinding?.nameEt?.error="Please enter name"
                viewDataBinding?.nameEt?.requestFocus()
            }

            viewDataBinding?.emailEt?.text.toString().isEmpty()->{
                viewDataBinding?.emailEt?.error="Please enter name"
                viewDataBinding?.emailEt?.requestFocus()
            }

            viewDataBinding?.userEt?.text.toString().isEmpty()->{
                viewDataBinding?.userEt?.error="Please enter username"
                viewDataBinding?.userEt?.requestFocus()
            }

            viewDataBinding?.ageEt?.text.toString().isEmpty()->{
                viewDataBinding?.ageEt?.error="Please enter age"
                viewDataBinding?.ageEt?.requestFocus()
            }

            viewDataBinding?.genderEt?.isEmpty() == true ->{
                viewDataBinding?.genderEt?.requestFocus()
            }

            viewDataBinding?.maritalEt?.isEmpty()==true ->{
                viewDataBinding?.maritalEt?.requestFocus()
            }

            viewDataBinding?.contactEt?.text.toString().isEmpty()->{
                viewDataBinding?.contactEt?.error="Please enter phone number"
                viewDataBinding?.contactEt?.requestFocus()
            }

            viewDataBinding?.passwordEt?.text.toString().isEmpty()->{
                viewDataBinding?.passwordEt?.error="Please enter password"
                viewDataBinding?.passwordEt?.requestFocus()
            }

            viewDataBinding?.confirmPasswordEt?.text.toString().isEmpty()->{
                viewDataBinding?.confirmPasswordEt?.error="Please enter confirm password"
                viewDataBinding?.confirmPasswordEt?.requestFocus()
            }
            !viewDataBinding?.passwordEt?.text.toString().equals(viewDataBinding?.confirmPasswordEt?.text.toString())->{
                putToast("password Does not Match")
            }



            else->return true
        }
        return false
    }


}