package com.example.almuhasabah.ui.prayerfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseFragment
import com.example.almuhasabah.databinding.FragmentPrayerBinding
import com.example.almuhasabah.viewpager.PageAdapter
import com.google.android.material.tabs.TabLayout

class PrayerFragment : BaseFragment<FragmentPrayerBinding,PrayerFragmentViewModel>() {

    private val prayerVM : PrayerFragmentViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.prayerVM
    override val layoutId: Int
        get() =R.layout.fragment_prayer
    override val viewModel: PrayerFragmentViewModel
        get() {
            return prayerVM
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)

        viewDataBinding?.viewPager?.adapter =PageAdapter(childFragmentManager)

        viewDataBinding?.tabLayout?.setupWithViewPager(viewDataBinding?.viewPager)

        return viewDataBinding?.root
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val viewPager = view?.findViewById<ViewPager>(R.id.viewPager)
//            viewPager?.adapter = PageAdapter(childFragmentManager)
//
//        val tabLayout = view?.findViewById<TabLayout>(R.id.tabLayout)
//        tabLayout?.setupWithViewPager(viewPager)
//
//    }




}