package com.example.almuhasabah.ui.qaza

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseFragment
import com.example.almuhasabah.databinding.FragmentQazaBinding
import java.nio.file.Paths.get
import java.util.*


class QazaFragment : BaseFragment<FragmentQazaBinding,QazaViewModel>() {

    private val qazaVM:QazaViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.qazaVM
    override val layoutId: Int
        get() = R.layout.fragment_qaza
    override val viewModel: QazaViewModel
        get() {
            return qazaVM
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       super.onCreateView(inflater, container, savedInstanceState)

        viewDataBinding?.fromDateEt?.setOnClickListener {
            val calendar= Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener
            { view, year, monthOfYear, dayOfMonth ->

                val date = "" + dayOfMonth + "/" + (monthOfYear+1) + "/" + year
                viewDataBinding?.fromDateEt?.setText(date)
            }, year, month, day)

            datePickerDialog.show()
        }

        viewDataBinding?.toDateEt?.setOnClickListener {
            val calendar= Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener
            { view, year, monthOfYear, dayOfMonth ->

                val date = "" + dayOfMonth + "/" + (monthOfYear+1) + "/" + year
                viewDataBinding?.toDateEt?.setText(date)
            }, year, month, day)

            datePickerDialog.show()
        }

       return viewDataBinding?.root
    }




}