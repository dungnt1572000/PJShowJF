package com.example.lastjfproject.adapter

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lastjfproject.ui.dowloaded.DowloadedWordFragment
import com.example.lastjfproject.ui.home.HomeFragment
import com.example.lastjfproject.ui.setting.SettingFragment

class myViewpagerAdapter(fr: Fragment) : FragmentStateAdapter(fr){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return HomeFragment()
            1 -> return DowloadedWordFragment()
            2 -> return SettingFragment()
        }
        return HomeFragment()
    }
}