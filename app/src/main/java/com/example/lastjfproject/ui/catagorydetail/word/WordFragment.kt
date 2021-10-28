package com.example.lastjfproject.ui.catagorydetail.word

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.lastjfproject.R
import com.example.lastjfproject.adapter.WordAdapter
import com.example.lastjfproject.databinding.FragmentWordBinding
import com.example.lastjfproject.myInterface.Word_Click_to_RoomDB
import com.example.lastjfproject.myObject.Book
import com.example.lastjfproject.myObject.Word
import com.example.lastjfproject.roomDB.roomDBLocal
import com.example.lastjfproject.ui.catagorydetail.CatagoryDetailViewModel
import com.google.firebase.firestore.*

class WordFragment : Fragment(),Word_Click_to_RoomDB {

    private lateinit var binding: FragmentWordBinding

    private lateinit var viewModel: CatagoryDetailViewModel

    private var myListWord = ArrayList<Word>()

    private lateinit var wordAdapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // init binding
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_word,container,false)
        viewModel = ViewModelProvider(requireParentFragment()).get(CatagoryDetailViewModel::class.java)

        Log.e("Day la book dc chuyen den",viewModel.mutableBook.value?.name.toString())

        // Inflate the layout for this fragment
        // initview
        initviewforRecycleView()
        searchEdtSearch()
        return binding.root
    }

    private fun searchEdtSearch() {

        binding.searchWordEdt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                Log.e("So luong trong myListWord la",myListWord.size.toString())
                findWord_and_addtoRecycle(p0)
            }

        })
    }


    private fun initviewforRecycleView() {
        wordAdapter = WordAdapter().apply {
            this.initData(myListWord)
            this.initClickWord(this@WordFragment)
        }
//        wordAdapter.initClickWord(this)
//        wordAdapter.initData(myListWord)
        binding.wordRecycleView.adapter = wordAdapter

        viewModel.getBook().observe(viewLifecycleOwner,{ it ->
           it?.let {
               getAllWordinBook(it)
           }
        })

    }

    private fun getAllWordinBook(book:Book) {

        val db = FirebaseFirestore.getInstance()

        db.collection(book.type.toString())
            .document(book.id.toString())
            .collection("tuvung")
            .addSnapshotListener { value, error ->

                if (error!=null){
                    Log.e("Null roi","Deo tim thay tu vung")
                    return@addSnapshotListener
                }

                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        myListWord.add(dc.document.toObject(Word::class.java))
                    }
                }

                wordAdapter.initData(myListWord)

            }
    }

    private fun findWord_and_addtoRecycle(p0: CharSequence?) {

        val newWordArrayList = ArrayList<Word>()

        if (p0.isNullOrEmpty()){
            newWordArrayList.clear()
            wordAdapter.initData(myListWord)
        }

        else {
            newWordArrayList.clear()
            for (arr in myListWord) {
                if (p0.let { arr.imi?.contains(it) } == true) {
                    newWordArrayList.add(arr)
                }
                if (p0.let { arr.name?.contains(it) } ==true){
                    newWordArrayList.add(arr)
                }
            }

            wordAdapter.initData(newWordArrayList)
        }


    }

    override fun Click_Word_to_RoomDB(word: Word) {
        requireContext().let {
            roomDBLocal.getInstance(it)?.myWordDAO()?.insertWord(word)
        }
        Log.e("Click Item Tuvung",word.name.toString())
        Toast.makeText(context,"You can see it when offline", Toast.LENGTH_SHORT).show()

    }

}