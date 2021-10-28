package com.example.lastjfproject.ui.processui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.lastjfproject.R
import com.example.lastjfproject.adapter.myViewpagerAdapter
import com.example.lastjfproject.databinding.ProcessUIFragmentBinding


class ProcessUIFragment : Fragment() {
    private lateinit var binding: ProcessUIFragmentBinding
    companion object {
        fun newInstance() = ProcessUIFragment()
    }

    private lateinit var viewModel: ProcessUIViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.process_u_i_fragment,container,false)
        Log.e("Day laf Process"," Process UI")
        setupViewPager()
        return binding.root
    }

    private fun setupViewPager() {
        val viewpager2Adapter = myViewpagerAdapter(this)
        binding.processUIViewpager.adapter = viewpager2Adapter
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_nav_home -> {
                    binding.processUIViewpager.currentItem = 0
                    activity?.let {
                        myactivity->
                        myactivity.supportFragmentManager.popBackStack()
                        myactivity.supportFragmentManager
                            .beginTransaction()
                            .addToBackStack("homefragment").commit()
                        Log.e("add Fragment","Home")
                    }

                    return@setOnItemSelectedListener true
                }
                R.id.bottom_nav_downloaded->{
                    binding.processUIViewpager.currentItem = 1
                    activity?.let { myactivity->
                        myactivity.supportFragmentManager.popBackStack()
                        myactivity.supportFragmentManager.beginTransaction()
                            .addToBackStack("dowloaded").commit()
                        Log.e("add Fragment","Dowload")
                    }

                    return@setOnItemSelectedListener true
                }
                R.id.bottom_nav_setting->{
                    binding.processUIViewpager.currentItem = 2
                    activity?.let { myactivity->
                        myactivity.supportFragmentManager.popBackStack()
                        myactivity.supportFragmentManager.beginTransaction()
                            .addToBackStack("setting").commit()
                        Log.e("add Fragment","Setting")
                    }
                    return@setOnItemSelectedListener true
                }
            }

            return@setOnItemSelectedListener false
        }
        binding.processUIViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> binding.bottomNavView.menu.findItem(R.id.bottom_nav_home).isChecked = true
                    1 -> binding.bottomNavView.menu.findItem(R.id.bottom_nav_downloaded).isChecked = true
                    2 -> binding.bottomNavView.menu.findItem(R.id.bottom_nav_setting).isChecked = true
                }
                super.onPageSelected(position)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProcessUIViewModel::class.java)
        // TODO: Use the ViewModel
    }

}