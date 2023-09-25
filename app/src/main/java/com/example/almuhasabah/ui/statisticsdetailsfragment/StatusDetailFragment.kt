package com.example.almuhasabah.ui.statisticsdetailsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseFragment
import com.example.almuhasabah.databinding.FragmentStatusDetailBinding


class StatusDetailFragment : BaseFragment<FragmentStatusDetailBinding,StatusDetailViewModel>() {

    private val statusdetailVM:StatusDetailViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.statusdetailVM
    override val layoutId: Int
        get() = R.layout.fragment_status_detail
    override val viewModel: StatusDetailViewModel
        get() {
            return statusdetailVM
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_detail, container, false)
    }


}