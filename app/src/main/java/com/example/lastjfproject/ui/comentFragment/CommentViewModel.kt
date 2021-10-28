package com.example.lastjfproject.ui.comentFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lastjfproject.user.Comment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class CommentViewModel : ViewModel() {

    private var mutableArrayList_Cmt = MutableLiveData<ArrayList<Comment>>()

    private val database = FirebaseDatabase.getInstance().getReference("Comment")

    fun getListDbCmt_from_DB(){
        val myArrList = ArrayList<Comment>()
        database
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    myArrList.clear()
                    for (data in snapshot.children){
                        data.getValue(Comment::class.java)?.let { myArrList.add(it) }
                        myArrList.sortBy { it.time }
                    }
                    mutableArrayList_Cmt.value = myArrList
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun getDataListCmt() = mutableArrayList_Cmt

}