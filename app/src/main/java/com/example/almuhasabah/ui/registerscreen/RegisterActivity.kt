package com.example.almuhasabah.ui.registerscreen

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseActivity
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.databinding.ActivityRegisterBinding
import com.example.almuhasabah.ui.loginscreen.LoginScreenActivity
import com.example.almuhasabah.utils.Constants
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : BaseActivity<ActivityRegisterBinding,RegisterViewModel>() {

    private val registerVM : RegisterViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.registerVM
    override val layoutId: Int
        get() = R.layout.activity_register
    override val viewModel: RegisterViewModel
        get() {
           return registerVM
        }

    var sharedPreferences : SharedPreferenceImp? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = SharedPreferenceImp()


        registerVM.mShowProgressBar.observeEvent(this, Observer {
            it.let {
                showProgressBar(register_layout)
            }
        })

        viewModel.email.set(sharedPreferences?.getString(Constants.EMAIL))

        viewDataBinding?.signupBtn?.setOnClickListener {

            if(registerValidation()){
                registerVM.registerApi()
            }
        }

        registerVM.registerDataResponse.observe(this, Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true->
                    {
                        putToast(it.data.message)
                        val i= Intent(this,LoginScreenActivity::class.java)
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

        viewDataBinding?.loginTv?.setOnClickListener {
            val i = Intent(this,LoginScreenActivity::class.java)
            startActivity(i)
        }

    }

    private fun registerValidation(): Boolean {
        when{
            viewDataBinding?.nameEt?.text.toString().isEmpty()->{
                viewDataBinding?.nameEt?.error="Please enter name"
                viewDataBinding?.nameEt?.requestFocus()
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

//    fun onClickView(view: View) {
//
//        val i = Intent(this,LoginScreenActivity::class.java)
//        startActivity(i)
//    }





}