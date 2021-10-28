package com.example.lastjfproject

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lastjfproject.user.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivityViewModel: ViewModel() {
    private var mutableUser = MutableLiveData<User>()


    fun getUserfromFirebase(userName: String, userPass: String){
        val database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(userName)
            .addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val myUser = snapshot.getValue(User::class.java)
                if (myUser?.userPass==userPass){
                    mutableUser.value = myUser!!
                    Log.e("Success",myUser.email.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Error", error.message)
            }

        })
    }

    fun getUserfromViewModel() = mutableUser
}