package com.example.almuhasabah.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.almuhasabah.ui.monthly.MonthlyFragment
import com.example.almuhasabah.ui.qaza.QazaFragment
import com.example.almuhasabah.ui.todayfragment.TodayFragment

class PageAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm){
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                TodayFragment()
            }
            1 -> {
                MonthlyFragment()
            }
            2 -> {
                QazaFragment()
            }
            else -> {
                TodayFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> {
                return "Today"
            }
            1 -> {
                return "Monthly"
            }
            2 -> {
                return "Qaza"
            }
        }

        return super.getPageTitle(position)
    }
}