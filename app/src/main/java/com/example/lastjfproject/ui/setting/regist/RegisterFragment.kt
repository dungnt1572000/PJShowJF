package com.example.lastjfproject.ui.setting.regist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import com.example.lastjfproject.R
import com.example.lastjfproject.databinding.FragmentRegisterBinding
import com.example.lastjfproject.user.Comment
import com.example.lastjfproject.user.User
import com.google.firebase.database.FirebaseDatabase


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val regexPatterns = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

    private val database = FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_register,container,false)

        binding.toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        binding.btnRegis.setOnClickListener {
            register_fun()
        }

        return binding.root
    }

    private fun register_fun() {

        val user = getUser()
        database.child(user.userName.toString()).get().addOnSuccessListener {
            if (it.exists()) {
                Toast.makeText(activity, "Tai Khoan nay da ton tai", Toast.LENGTH_LONG).show()
            } else {
                addUsertoDB(user)
            }
        }

    }

    private fun addUsertoDB(user: User) {
        if (!error_passnotsame(
                binding.password.text.toString(),
                binding.editRepass.text.toString()
            )
            && setTextonchange() == 0
        ) {
            database.child(user.userName.toString()).setValue(user)
                .addOnSuccessListener {
                    Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
                    activity?.let {
                        it.supportFragmentManager.popBackStack()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failure", Toast.LENGTH_LONG).show()
                }
        }

    }

    private fun getUser(): User {
        val user_name = binding.userName.text.toString()
        val user_pass = binding.password.text.toString()
        val user_age = binding.userAge.text.toString().toInt()
        val user_email = binding.userEmail.text.toString()
        val user = User(
            userName = user_name,
            userPass = user_pass,
            age = user_age,
            avatarUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/12/User_icon_2.svg/1200px-User_icon_2.svg.png",
            comment = ArrayList(),
            email = user_email
        )
        return user
    }


    fun error_passnotsame(str: String, str1: String): Boolean {
        if (!str1.equals(str)) {
            binding.repass.error = "2 Pass Not Same"
            return true
        } else
            binding.repass.helperText = "OK"
        return false
    }

    fun setTextonchange(): Int {


        if (!binding.userAge.text!!.isDigitsOnly()) {
            binding.textInputLayout5.error = "Only Digital"
            return 1
        }
        if (!binding.userEmail.text!!.matches(regexPatterns)) {
            binding.textInputLayout6.error = "Email Wrong"
            return 2
        }
        return 0
    }
}