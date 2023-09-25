package com.example.almuhasabah.ui.homescreen

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.almuhasabah.BR
import com.example.almuhasabah.base.BaseActivity
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.databinding.ActivityHomeBinding
import com.example.almuhasabah.ui.homefragment.HomeFragment
import com.example.almuhasabah.ui.loginscreen.LoginScreenActivity
import com.example.almuhasabah.ui.prayerfragment.PrayerFragment
import com.example.almuhasabah.ui.profilefragment.ProfileFragment
import com.example.almuhasabah.ui.selfaccessfragment.SelfAccessFragment
import com.example.almuhasabah.ui.statusfragment.StatusFragment
import com.example.almuhasabah.utils.Constants





class HomeActivity : BaseActivity<ActivityHomeBinding,HomeViewModel>() {

    private val homeVM : HomeViewModel by viewModels()
    var sharedPreferences : SharedPreferenceImp? = null

    override val bindingVariable: Int
        get() = BR.homeVM
    override val layoutId: Int
        get() = com.example.almuhasabah.R.layout.activity_home
    override val viewModel: HomeViewModel
        get() {
            return homeVM
        }
    private var backToast:Toast?=null
    private var backPressedTime:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences= SharedPreferenceImp()

        val homeScreen = HomeFragment()
        val prayerScreen = PrayerFragment()
        val selfAccess =SelfAccessFragment()
        val status = StatusFragment()
        val profile = ProfileFragment()
        showFragment(homeScreen)

        viewModel.api.set(sharedPreferences?.getString(Constants.API_TOKEN)).toString()
        Log.d("Api Token",sharedPreferences?.getString(Constants.API_TOKEN).toString())

        viewDataBinding?.bottomNavigationView?.setOnNavigationItemSelectedListener {
            when(it.itemId){
                com.example.almuhasabah.R.id.home -> showFragment(homeScreen)
                com.example.almuhasabah.R.id.prayer -> showFragment(prayerScreen)
                com.example.almuhasabah.R.id.self -> showFragment(selfAccess)
                com.example.almuhasabah.R.id.status -> showFragment(status)
                com.example.almuhasabah.R.id.profile -> showFragment(profile)
            }
            true
        }

//        val navHostFragment = supportFragmentManager
//            .findFragmentById(com.example.almuhasabah.R.id.nav_host_fragment) as NavHostFragment?
//        NavigationUI.setupWithNavController(
//            bottomNavigationView,
//            navHostFragment!!.navController
//        )

    }

    private fun showAlertDialogueToLogout() {
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Log out?")
            .setMessage("Are you sure you want to Log out?")
            .setPositiveButton("Yes") { _, _ ->
                val i =Intent(this,LoginScreenActivity::class.java)
                startActivity(i)
                sharedPreferences?.clear()
                finish()
                //sharedPreferences.setBooleanValue(Constants.ALREADY_LOGED_IN,false)
               // goTo(LoginActivity::class.java,null)
            }
            .setNegativeButton("No", null)
            .show()
    }

//    override fun onResume() {
//        bottomNavigationView.selectedItemId = R.id.home
//        super.onResume()
//    }

//    override fun onBackPressed() {
//        showAlertDialogueToLogout()
//    }

    override fun onBackPressed() {
        backToast = Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG)
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            backToast?.cancel()
            super.onBackPressed()
            return
        } else {
            backToast?.show()
        }
        backPressedTime = System.currentTimeMillis()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //change your desigen by java code
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //change your desigen by java code
        }
    }


}