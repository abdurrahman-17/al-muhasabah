package com.example.almuhasabah.ui.forgetpassword

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseActivity
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.databinding.ActivityForgetPasswordBinding
import com.example.almuhasabah.ui.updatepassword.UpdatePasswordActivity
import com.example.almuhasabah.utils.Constants
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPasswordActivity :BaseActivity<ActivityForgetPasswordBinding,ForgetPasswordViewModel>() {

    private val forgetPasswordVM : ForgetPasswordViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.forgetPasswordVM
    override val layoutId: Int
        get() = R.layout.activity_forget_password
    override val viewModel: ForgetPasswordViewModel
        get() {
            return forgetPasswordVM
        }

    var sharedPreferences : SharedPreferenceImp? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = SharedPreferenceImp()

        viewDataBinding?.backIv?.setOnClickListener {
            onBackPressed()
        }

        forgetPasswordVM.mShowProgressBar.observeEvent(this, Observer {
            it.let {
                showProgressBar(forget_password_layout)
            }
        })

        viewDataBinding?.submitBtn?.setOnClickListener {
            if (forgetPasswordValidation()){
                forgetPasswordVM.forgetPasswordApi()
            }
        }

        forgetPasswordVM.forgetPassDataResponse.observe(this, Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true->
                    {
                        putToast(it.data.message)
                        sharedPreferences?.setString(Constants.EMAIL , it.data.response.email)
                        sharedPreferences?.setString(Constants.OTP , it.data.response.otp)

                        val i =Intent(this, UpdatePasswordActivity::class.java)
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

    private fun forgetPasswordValidation(): Boolean {
        when{
            viewDataBinding?.userEt?.text.toString().isEmpty()->{
                viewDataBinding?.userEt?.error="Enter valid email"
                viewDataBinding?.userEt?.requestFocus()
            }

        else->return true
        }
        return false
    }

}