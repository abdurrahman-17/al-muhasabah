package com.example.almuhasabah.ui.todaydetailfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseFragment
import com.example.almuhasabah.databinding.FragmentTodayDetailBinding


class TodayDetailFragment : BaseFragment<FragmentTodayDetailBinding,TodayDetailViewModel>() {

    private val todaydetailVM:TodayDetailViewModel by viewModels()



    override val bindingVariable: Int
        get() = BR.todaydetailVM
    override val layoutId: Int
        get() = R.layout.fragment_today_detail
    override val viewModel: TodayDetailViewModel
        get(){
            return todaydetailVM
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       super.onCreateView(inflater, container, savedInstanceState)



       return viewDataBinding?.root
    }

}