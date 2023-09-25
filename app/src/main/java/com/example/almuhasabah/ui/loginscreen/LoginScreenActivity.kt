package com.example.almuhasabah.ui.loginscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseActivity
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.databinding.ActivityLoginScreenBinding
import com.example.almuhasabah.ui.emailvalidation.EmailValidationActivity
import com.example.almuhasabah.ui.forgetpassword.ForgetPasswordActivity
import com.example.almuhasabah.ui.homescreen.HomeActivity
import com.example.almuhasabah.utils.Constants
import kotlinx.android.synthetic.main.activity_login_screen.*


class LoginScreenActivity : BaseActivity<ActivityLoginScreenBinding, LoginViewModel>() {

    private val loginVM: LoginViewModel by viewModels()
    var sharedPreferences : SharedPreferenceImp? = null
    override val bindingVariable: Int
        get() = BR.loginVM
    override val layoutId: Int
        get() = R.layout.activity_login_screen
    override val viewModel: LoginViewModel
        get() {
            return loginVM
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = SharedPreferenceImp()


        viewDataBinding?.forgotTv?.setOnClickListener {
            val i =Intent(this,ForgetPasswordActivity::class.java)
            startActivity(i)
        }

        loginVM.mShowProgressBar.observeEvent(this, Observer {
            it.let {
                showProgressBar(loginScreen_layout)
            }
        })


        viewDataBinding?.loginBtn?.setOnClickListener {

           if (loginValidation()){

               viewModel.loginApi()
           }
        }

        loginVM.loginDataResponse.observe(this, Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true->
                    {
                        putToast(it.data.message)
                        sharedPreferences?.setString(Constants.NAME , it.data.response.name)
                        sharedPreferences?.setString(Constants.EMAIL , it.data.response.email)
                        sharedPreferences?.setString(Constants.API_TOKEN , it.data.response.api_token)
                        sharedPreferences?.setString(Constants.DOB,it.data.response.age)
                        sharedPreferences?.setString(Constants.MARITAL_STATUS,it.data.response.marital_status)
                        sharedPreferences?.setString(Constants.GENDER,it.data.response.gender)
                        sharedPreferences?.setString(Constants.ID , it.data.response.id)
                        sharedPreferences?.setString(Constants.PHONE , it.data.response.phone_no)
                        val i= Intent(this, HomeActivity::class.java)
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

    private fun loginValidation(): Boolean {
        when{
            viewDataBinding?.userEt?.text.toString().isEmpty()->{
                viewDataBinding?.userEt?.error="Please enter username"
                viewDataBinding?.userEt?.requestFocus()
            }

            viewDataBinding?.passwordET?.text.toString().isEmpty()->{
                viewDataBinding?.passwordET?.error="Please enter password"
                viewDataBinding?.passwordET?.requestFocus()
            }

            else->return true
        }
        return false
    }

    fun onClickView(view: View) {
            val i =Intent(this,EmailValidationActivity::class.java)
            startActivity(i)
    }

}