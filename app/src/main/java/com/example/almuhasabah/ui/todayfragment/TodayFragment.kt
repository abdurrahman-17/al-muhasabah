package com.example.almuhasabah.ui.todayfragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseFragment
import com.example.almuhasabah.databinding.FragmentTodayBinding
import com.example.almuhasabah.ui.todaydetailfragment.TodayDetailFragment


class TodayFragment : BaseFragment<FragmentTodayBinding,TodayViewModel>() {

    private val todayVM : TodayViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.todayVM
    override val layoutId: Int
        get() = R.layout.fragment_today
    override val viewModel: TodayViewModel
        get() {
            return todayVM
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       super.onCreateView(inflater, container, savedInstanceState)

       viewDataBinding?.materialCardView6?.setOnClickListener {
         replaceFragment(R.id.bottom_container,TodayDetailFragment(),"detail","detail")
       }

        return viewDataBinding?.root
    }



}