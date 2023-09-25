package com.example.almuhasabah.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.almuhasabah.R
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.ui.homescreen.HomeActivity
import com.example.almuhasabah.ui.loginscreen.LoginScreenActivity
import com.example.almuhasabah.utils.Constants

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    var sharedPreferences : SharedPreferenceImp? = null
    private var token : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash_screen)
        sharedPreferences = SharedPreferenceImp()
        token = sharedPreferences?.getString(Constants.API_TOKEN).toString()
        fullscreenUI()

        Handler().postDelayed({
            if (token == ""){
            val intent = Intent(this, LoginScreenActivity::class.java)
            startActivity(intent)
            finish()
            }
            else{
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
            }
        }, 3000)
    }

    /**
     *  set flag to see splash screen on full view.
     */
    private fun fullscreenUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }


}