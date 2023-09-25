package com.example.almuhasabah.ui.selfaccessfragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseFragment
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.databinding.FragmentSelfAccessBinding
import com.example.almuhasabah.ui.questionarylist.QuestionaryListFragment
import com.example.almuhasabah.utils.Constants


class SelfAccessFragment : BaseFragment<FragmentSelfAccessBinding,SelfAccessViewModel>() {

   private val selfaccessVM:SelfAccessViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.selfaccessVM
    override val layoutId: Int
        get() = R.layout.fragment_self_access
    override val viewModel: SelfAccessViewModel
        get() {
            return selfaccessVM
        }
    var sharedPreferences : SharedPreferences?=null
    var sharedPreference : SharedPreferenceImp?=null
    var editor : SharedPreferences.Editor?=null
    private var token : String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        token = sharedPreference?.getString(Constants.API_TOKEN).toString()


//        viewDataBinding?.submitButton?.setOnClickListener {
//
//
//            replaceFragment(R.id.bottom_container, QuestionaryListFragment(),"questionList","questionList")
//
//        }

        viewDataBinding?.submitButton?.setOnClickListener {

            validation()
        }

        if (token==""){
            editor?.clear()
            editor?.commit()

        }
        else
        {
            sharedPreferences=context?.getSharedPreferences("pref",0)
            val radiosp = sharedPreferences?.getInt(Constants.RADIO_SP,3)
            editor=sharedPreferences?.edit()

            if (radiosp==1){
                viewDataBinding?.Q1radioButton1?.isChecked=true
            }else if (radiosp == 0){
                viewDataBinding?.Q1radioButton2?.isChecked=true
            }

            if (radiosp==1){
                viewDataBinding?.Q2radioButton1?.isChecked=true
            }else if (radiosp == 0){
                viewDataBinding?.Q2radioButton2?.isChecked=true
            }

            if (radiosp==1){
                viewDataBinding?.Q3radioButton1?.isChecked=true
            }else if (radiosp == 0){
                viewDataBinding?.Q3radioButton2?.isChecked=true
            }

            if (radiosp==1){
                viewDataBinding?.Q4radioButton1?.isChecked=true
            }else if (radiosp == 0){
                viewDataBinding?.Q4radioButton2?.isChecked=true
            }


            viewDataBinding?.rg1?.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->

                if (i==R.id.Q1radioButton1){
                    editor?.putInt(Constants.RADIO_SP,1)
                }else if (i==R.id.Q1radioButton2){
                    editor?.putInt(Constants.RADIO_SP,0)
                }
                editor?.commit()
            })


            viewDataBinding?.rg2?.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->

                if (i==R.id.Q2radioButton1){
                    editor?.putInt(Constants.RADIO_SP,1)
                }else if (i==R.id.Q2radioButton2){
                    editor?.putInt(Constants.RADIO_SP,0)
                }
                editor?.commit()
            })


            viewDataBinding?.rg3?.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->

                if (i==R.id.Q3radioButton1){
                    editor?.putInt(Constants.RADIO_SP,1)
                }else if (i==R.id.Q3radioButton2){
                    editor?.putInt(Constants.RADIO_SP,0)
                }
                editor?.commit()
            })


            viewDataBinding?.rg4?.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->

                if (i==R.id.Q4radioButton1){
                    editor?.putInt(Constants.RADIO_SP,1)
                }else if (i==R.id.Q4radioButton2){
                    editor?.putInt(Constants.RADIO_SP,0)
                }
                editor?.commit()
            })


        }





        return viewDataBinding?.root
    }


    private fun validation() {

        var id: Int = viewDataBinding?.rg1?.checkedRadioButtonId!!
        id=viewDataBinding?.rg2?.checkedRadioButtonId!!
        id=viewDataBinding?.rg3?.checkedRadioButtonId!!
        id=viewDataBinding?.rg4?.checkedRadioButtonId!!

        if (id!=-1){
            replaceFragment(R.id.bottom_container, QuestionaryListFragment(), "questionList", "questionList")
        }else{
            putToast("Select all the answer")
        }
    }

}