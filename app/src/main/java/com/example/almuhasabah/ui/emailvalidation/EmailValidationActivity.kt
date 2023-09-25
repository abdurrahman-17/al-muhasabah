package com.example.almuhasabah.ui.emailvalidation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseActivity
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.databinding.ActivityEmailValidationBinding
import com.example.almuhasabah.ui.emailvalidationotp.EmailValidationOtpActivity
import com.example.almuhasabah.utils.Constants
import kotlinx.android.synthetic.main.activity_email_validation.*


class EmailValidationActivity : BaseActivity<ActivityEmailValidationBinding,EmailValidationViewModel>() {

    val emailValidationVM:EmailValidationViewModel by viewModels()


    override val bindingVariable: Int
        get() = BR.emailValidationVM
    override val layoutId: Int
        get() = R.layout.activity_email_validation
    override val viewModel: EmailValidationViewModel
        get() {
            return emailValidationVM
        }

    var sharedPreferences : SharedPreferenceImp? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = SharedPreferenceImp()


        viewDataBinding?.backIv?.setOnClickListener {
            onBackPressed()
        }

        emailValidationVM.mShowProgressBar.observeEvent(this, Observer {
            it.let {
                showProgressBar(email_layout)
            }
        })

        viewDataBinding?.submitBtn?.setOnClickListener {

            if(emailValidation()){
                emailValidationVM.emailValidationApi()
            }

        }

        emailValidationVM.emailDataResponse.observe(this, Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true->
                    {
                        putToast(it.data.message)
                        sharedPreferences?.setString(Constants.EMAIL , it.data.response.email)
                        sharedPreferences?.setString(Constants.OTP , it.data.response.otp)
                        val i =Intent(this,EmailValidationOtpActivity::class.java)
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

    private fun emailValidation(): Boolean {
        when{
            viewDataBinding?.userEt?.text.toString().isEmpty()->{
            viewDataBinding?.userEt?.error="Please enter valid email"
            viewDataBinding?.userEt?.requestFocus()
            }
        else->return true
        }
        return false
    }

}