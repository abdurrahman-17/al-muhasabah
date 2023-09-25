package com.example.almuhasabah.ui.editsecondaryuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseActivity
import com.example.almuhasabah.databinding.ActivityEditSecondaryUserBinding
import com.example.almuhasabah.databinding.ActivitySecondaryUserBinding

class EditSecondaryUserActivity : BaseActivity<ActivityEditSecondaryUserBinding,EditSecondaryUserViewModel>() {

    private val editsecondaryuserVM : EditSecondaryUserViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.editsecondaryuserVM
    override val layoutId: Int
        get() = R.layout.activity_edit_secondary_user
    override val viewModel: EditSecondaryUserViewModel
        get() {
            return editsecondaryuserVM
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_secondary_user)
    }


}