package com.example.almuhasabah.ui.qazadatedetailfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseFragment
import com.example.almuhasabah.databinding.FragmentQazaDateDetailBinding


class QazaDateDetailFragment : BaseFragment<FragmentQazaDateDetailBinding,QazaDateDetailViewModel>() {

    private val qazadatedetailVM : QazaDateDetailViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.qazadatedetailVM
    override val layoutId: Int
        get() = R.layout.fragment_qaza_date_detail
    override val viewModel: QazaDateDetailViewModel
        get() {
            return qazadatedetailVM
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qaza_date_detail, container, false)
    }




}