package com.example.lastjfproject.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lastjfproject.R
import com.example.lastjfproject.ui.home.HomeFragment
import com.example.lastjfproject.ui.processui.ProcessUIFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SlashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        Log.e("Hello"," Shlash Fragment")
        Looper.getMainLooper()?.let {
            Handler(it).postDelayed({
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.contain_activity_layout,ProcessUIFragment())?.commit()
            },1500)
        }
        return inflater.inflate(R.layout.fragment_slash, container, false)
    }
}