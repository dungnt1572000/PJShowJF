package com.example.lastjfproject.ui.setting

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lastjfproject.MainActivityViewModel
import com.example.lastjfproject.R
import com.example.lastjfproject.databinding.FragmentSettingBinding
import com.example.lastjfproject.myObject.CurrentUser
import com.example.lastjfproject.storageData.DatalocalSharePrefManager
import com.example.lastjfproject.ui.setting.regist.RegisterFragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.*


class SettingFragment : Fragment() {


    private lateinit var binding: FragmentSettingBinding

    private lateinit var viewModel: MainActivityViewModel

    private var RESULT_CODE = 12345

    private var imgUri: Uri? = null

    private val reference: StorageReference = FirebaseStorage.getInstance().reference

    private val database = FirebaseDatabase.getInstance().getReference("Users")

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    //    private val sharePref = UserSharePreference(requireActivity().applicationContext)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)

        // Inflate the layout for this fragment
        Log.e("On Create", "On create Setting Fragment")
        if (DatalocalSharePrefManager.getUserLoginedSharePref() || CurrentUser.getUser() != null) {
            initViewLogined()
        } else {
            Log.e("DatalocalSharePrefManager.getUserLoginedSharePref()", "false")
            initViewNotLogin()
        }

        return binding.root
    }

    private fun initViewNotLogin() {
        // init View
        binding.loginedLayoutId.layoutLoginedContainer.visibility = View.GONE
        binding.notLoginLayoutId.notLoginedLayoutContainer.visibility = View.VISIBLE
        CurrentUser.deleteUser();

        binding.notLoginLayoutId.tvRegister.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.contain_activity_layout, RegisterFragment())
                ?.addToBackStack("register")?.commit()
        }

        binding.notLoginLayoutId.btnLogin.setOnClickListener {

            val userName = binding.notLoginLayoutId.textInputUserName.text.toString()
            val userPass = binding.notLoginLayoutId.textInputUserPass.text.toString()

            binding.notLoginLayoutId.progressBar.visibility = View.VISIBLE
            viewModel.getUserfromFirebase(userName, userPass)
            loginbyUser(userName, userPass)
        }
    }

    private fun loginbyUser(str1: String, str2: String) {

        viewModel.getUserfromViewModel().observe(viewLifecycleOwner, {
            Log.e("OK", "Loggined")
            if (binding.notLoginLayoutId.checkBox.isChecked) {
                DatalocalSharePrefManager.saveStringUserName(str1)
                DatalocalSharePrefManager.saveStringUserPass(str2)
                DatalocalSharePrefManager.saveBooleanUserSaved(true)
            } else {
                DatalocalSharePrefManager.saveStringUserName(str1)
                DatalocalSharePrefManager.saveStringUserPass("")
                DatalocalSharePrefManager.saveBooleanUserSaved(false)
            }
            binding.loginedLayoutId.user = it
            CurrentUser.initUser(it)
            initViewLogined()
            Log.e("This is User", it.userName.toString())
        })
        GlobalScope.launch(Dispatchers.Main) {
            delay(5000)
            if (CurrentUser.getUser()==null){
                Toast.makeText(context,"Some thing wrong",Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun initViewLogined() {
        Log.e("Logined View","Logined view")
        binding.notLoginLayoutId.notLoginedLayoutContainer.visibility = View.GONE
        binding.loginedLayoutId.layoutLoginedContainer.visibility = View.VISIBLE
        binding.loginedLayoutId.user = CurrentUser.getUser()
        //log_out
        binding.loginedLayoutId.tvLogOut.setOnClickListener {
            DatalocalSharePrefManager.saveStringUserName("")
            DatalocalSharePrefManager.saveStringUserPass("")
            DatalocalSharePrefManager.saveBooleanUserSaved(false)
            activity?.finish()
            activity?.startActivity(requireActivity().intent)
        }
        // change img


        binding.loginedLayoutId.userImgChange.setOnClickListener {
            Log.e("Clicked", "Click Change IMG")
            getGallery()
        }

        // change profile
        binding.loginedLayoutId.btnChangeProfile.setOnClickListener {
            Log.e("Is change pass btn", " Clicked")
            if (imgUri != null) {
                getChangedsome()
                uploadtoFirebase(imgUri!!)
            } else {
                getChangedsome()
                updatetoServer()
            }
        }

    }

    private fun getChangedsome() {
        CurrentUser.getUser()!!.age = binding.loginedLayoutId.editTuoi.text.toString().toInt()
        CurrentUser.getUser()!!.email = binding.loginedLayoutId.editEmail.text.toString()
        CurrentUser.getUser()!!.userPass = binding.loginedLayoutId.editNewpass.text.toString()
    }

    private fun uploadtoFirebase(uri: Uri) {

        val fileRef =
            reference.child(System.currentTimeMillis().toString() + "." + getFileExtensions(uri))
        Log.e("Day la file ref ",
            System.currentTimeMillis().toString() + "." + getFileExtensions(uri))
        fileRef.putFile(uri).addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener {
                CurrentUser.getUser()?.avatarUrl = it.toString()
                updatetoServer()
            }
        }

    }

    private fun updatetoServer() {
        database.child(CurrentUser.getUser()!!.userName.toString())
            .setValue(CurrentUser.getUser()!!).addOnSuccessListener {
                Toast.makeText(context, "Success Changed", Toast.LENGTH_SHORT).show()
                activity?.finish()
                activity?.startActivity(requireActivity().intent)
            }
    }

    private fun getFileExtensions(uri: Uri): String {
        val cr: ContentResolver = requireContext().contentResolver
        val mimeType: MimeTypeMap = MimeTypeMap.getSingleton()
        return mimeType.getExtensionFromMimeType(cr.getType(uri)).toString()
    }

    private fun getGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, RESULT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == RESULT_CODE) {
            imgUri = data?.data
            binding.loginedLayoutId.userImg.setImageURI(imgUri) // handle chosen image
        }
    }
}