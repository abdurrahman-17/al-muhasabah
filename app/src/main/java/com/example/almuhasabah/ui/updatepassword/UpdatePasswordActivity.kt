package com.example.almuhasabah.ui.updatepassword

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseActivity
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.databinding.ActivityUpdatePasswordBinding
import com.example.almuhasabah.ui.loginscreen.LoginScreenActivity
import com.example.almuhasabah.utils.Constants
import kotlinx.android.synthetic.main.activity_update_password.*

class UpdatePasswordActivity : BaseActivity<ActivityUpdatePasswordBinding,UpdatePasswordViewModel>() {

    private  val updatepasswordVM : UpdatePasswordViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.updatepasswordVM
    override val layoutId: Int
        get() =R.layout.activity_update_password
    override val viewModel: UpdatePasswordViewModel
        get() {
            return updatepasswordVM
        }

    var sharedPreferences : SharedPreferenceImp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = SharedPreferenceImp()

        viewDataBinding?.backIv?.setOnClickListener {
            onBackPressed()
        }

        updatepasswordVM.mShowProgressBar.observeEvent(this, Observer {
            it.let {
                showProgressBar(update_password_layout)
            }
        })

        viewModel.email.set(sharedPreferences?.getString(Constants.EMAIL).toString())
        viewModel.otp.set(sharedPreferences?.getString(Constants.OTP).toString())

        viewDataBinding?.submitBtn?.setOnClickListener {
            if(updatePasswordValidation()){
                viewModel.updatePasswordApi()
            }
        }

        updatepasswordVM.updatePasswordDataResponse.observe(this, Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true->
                    {
                        putToast(it.data.message)
                        finish()
                        val i= Intent(this, LoginScreenActivity::class.java)
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

    private fun updatePasswordValidation(): Boolean {
    when{

        viewDataBinding?.otpNumber?.text.toString().isEmpty()->{
            viewDataBinding?.otpNumber?.error="Please enter otp"
            viewDataBinding?.otpNumber?.requestFocus()
        }

        viewDataBinding?.passwordET?.text.toString().isEmpty()->{
            viewDataBinding?.passwordET?.error="Please enter password"
            viewDataBinding?.passwordET?.requestFocus()
        }

        viewDataBinding?.confirmPasswordET?.text.toString().isEmpty()->{
            viewDataBinding?.confirmPasswordET?.error="Please enter otp"
            viewDataBinding?.confirmPasswordET?.requestFocus()
        }

        !viewDataBinding?.passwordET?.text.toString().equals(viewDataBinding?.confirmPasswordET?.text.toString()) ->{
           putToast("Password does not match")
        }

       else-> return true
    }
        return false
    }

}