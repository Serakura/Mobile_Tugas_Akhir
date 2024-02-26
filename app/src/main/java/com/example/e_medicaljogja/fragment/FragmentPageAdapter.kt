package com.example.e_medicaljogja.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

internal class FragmentPageAdapter(val context: Context, val fm: FragmentManager, val totalTabs: Int, val kode_rs: String): FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {

        val bundle = Bundle()
        bundle.putString("kode_rs", kode_rs)
        return when(position){
            0 -> {
                val myFragment = PelayananFragment()
                myFragment.arguments = bundle
                return myFragment
            }
            1 -> {
                val myFragment = FasilitasFragment()
                myFragment.arguments = bundle
                return myFragment
            }
            else -> {
                val myFragment = PelayananFragment()
                myFragment.arguments = bundle
                return myFragment
            }
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}