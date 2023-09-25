package com.example.almuhasabah.ui.emailvalidationotp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseActivity
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.databinding.ActivityEmailValidationOtpBinding
import com.example.almuhasabah.ui.registerscreen.RegisterActivity
import com.example.almuhasabah.utils.Constants
import kotlinx.android.synthetic.main.activity_email_validation_otp.*

class EmailValidationOtpActivity : BaseActivity<ActivityEmailValidationOtpBinding,EmailValidationOtpViewModel>() {

    private val emailValidationOtpVM :EmailValidationOtpViewModel by viewModels()


    override val bindingVariable: Int
        get() = BR.emailValidationOtpVM
    override val layoutId: Int
        get() = R.layout.activity_email_validation_otp
    override val viewModel: EmailValidationOtpViewModel
        get() {
            return emailValidationOtpVM
        }

    var sharedPreferences : SharedPreferenceImp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = SharedPreferenceImp()


        viewDataBinding?.backIv?.setOnClickListener {
            onBackPressed()
        }

        emailValidationOtpVM.mShowProgressBar.observeEvent(this, Observer {
            it.let {
                showProgressBar(otp_layout)
            }
        })
        viewModel.email.set(sharedPreferences?.getString(Constants.EMAIL))
        viewModel.otpTrue.set(sharedPreferences?.getString(Constants.OTP))
        viewDataBinding?.submitBtn?.setOnClickListener {

            if(otpValidation()){
                emailValidationOtpVM.otpValidationApi()
            }
        }

        emailValidationOtpVM.otpDataResponse.observe(this, Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true->
                    {
                        putToast(it.data.message)
                        sharedPreferences?.setString(Constants.EMAIL,it.data.response.email)
                        val i= Intent(this,RegisterActivity::class.java)
                        startActivity(i)
                        finish()

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

    private fun otpValidation(): Boolean {
        when{
            viewDataBinding?.userEt?.text.toString().isEmpty()->{
                viewDataBinding?.userEt?.error="Please enter valid otp"
                viewDataBinding?.userEt?.requestFocus()
            }
            else->return true
        }
        return false
    }


}