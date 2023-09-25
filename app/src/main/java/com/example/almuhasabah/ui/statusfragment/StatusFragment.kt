package com.example.almuhasabah.ui.statusfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseFragment
import com.example.almuhasabah.databinding.FragmentStatusBinding


class StatusFragment : BaseFragment<FragmentStatusBinding,StatusViewModel> (){

    private val statusVM:StatusViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.statusVM
    override val layoutId: Int
        get() = R.layout.fragment_status
    override val viewModel: StatusViewModel
        get() {
            return statusVM
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false)
    }



}