package com.example.almuhasabah.ui.monthly

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseFragment
import com.example.almuhasabah.databinding.FragmentMonthlyBinding


class MonthlyFragment : BaseFragment<FragmentMonthlyBinding,MonthlyViewModel>() {

    private val monthlyVM :MonthlyViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.monthlyVM
    override val layoutId: Int
        get() = R.layout.fragment_monthly
    override val viewModel: MonthlyViewModel
        get() {
            return monthlyVM
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        setupCalendar()



        return viewDataBinding?.root
    }

    private fun setupCalendar() {
        viewDataBinding?.calenderView?.setOnDateChangeListener { view, year, month, dayOfMonth ->

            val msg = "Selected Date is : " + dayOfMonth + "/" + (month + 1) + "/" + year

            viewDataBinding?.calenderTxt?.text = msg

        }
    }


}