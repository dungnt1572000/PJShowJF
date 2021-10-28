package com.example.lastjfproject.ui.comentFragment

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lastjfproject.MainActivityViewModel
import com.example.lastjfproject.R
import com.example.lastjfproject.adapter.CommentAdapter
import com.example.lastjfproject.databinding.CommentFragmentBinding
import com.example.lastjfproject.myObject.CurrentUser
import com.example.lastjfproject.storageData.DatalocalSharePrefManager
import com.example.lastjfproject.user.Comment
import com.example.lastjfproject.user.User
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class CommentFragment : Fragment() {

    private lateinit var adapter: CommentAdapter


    private lateinit var viewModel: CommentViewModel

    private lateinit var viewModelActivity: MainActivityViewModel

    private lateinit var binding: CommentFragmentBinding

    private val database = FirebaseDatabase.getInstance().getReference("Comment")


    private val handler by lazy { Handler() }

    private var runnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CommentViewModel::class.java)
        viewModelActivity = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        viewModelActivity.getUserfromFirebase(DatalocalSharePrefManager.getUserNamefromSharePref().toString(),
            DatalocalSharePrefManager.getUserPassfromSharePref().toString())
        viewModel.getListDbCmt_from_DB()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.e("Wel come", "Comment Fragment")
        binding = DataBindingUtil.inflate(inflater, R.layout.comment_fragment, container, false)

        //getCurUser
        adapter = CommentAdapter()

        //update UI every 2second
        runnable = Runnable {
            viewModel.getDataListCmt().observe(viewLifecycleOwner, {

                for (data in it){
                    if (data.user?.userName == DatalocalSharePrefManager.getUserNamefromSharePref()){
                        data.viewType =1
                    }
                }

                adapter.initData(it)

            })
            Log.e("OK bro", " Let see")
            handler.postDelayed(runnable!!, 2000)
        }

        handler.postDelayed(runnable!!, 2000)

        binding.recyclerView.adapter = adapter

        binding.btnSend.setOnClickListener {
            val message = binding.messageEdt.text.toString()
            if (message.isEmpty()) {
                Toast.makeText(context, "press some text", Toast.LENGTH_SHORT).show()
            } else {
                binding.messageEdt.text?.clear()
                sendCommenttoDB(message)
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }


        return binding.root
    }


    private fun sendCommenttoDB(message: String) {

//        val user_Name = viewModel.getUserfromViewModel().value?.userName
        val currentTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
        val formatted = currentTime.format(formatter)


        database.push().setValue(Comment(message, formatted, 2, CurrentUser.getUser())).addOnSuccessListener {
            Toast.makeText(context, "OKe Updingg", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "NOOOOO Updingg", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        runnable?.let { handler.removeCallbacks(it) }
    }


}
