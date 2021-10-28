package com.example.lastjfproject.ui.catagorydetail


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lastjfproject.R
import com.example.lastjfproject.adapter.BookAdapter
import com.example.lastjfproject.databinding.FragmentCatagoryDetailBinding
import com.example.lastjfproject.myInterface.GetClickBook
import com.example.lastjfproject.myObject.Book
import com.example.lastjfproject.ui.catagorydetail.word.WordFragment
import com.example.lastjfproject.ui.home.HomeViewModel
import com.google.firebase.firestore.*


private const val ARG_STRING_CATAGORY_NAME =
    "catagory_name" // name of catagory to get Data from database


class CatagoryDetailFragment : Fragment(), GetClickBook {

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModel2: CatagoryDetailViewModel

    private lateinit var binding: FragmentCatagoryDetailBinding

    private val database = FirebaseFirestore.getInstance()

    private lateinit var bookAdapter: BookAdapter

    private var myListBook = ArrayList<Book>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        viewModel2 = ViewModelProvider(this).get(CatagoryDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_catagory_detail, container, false)
        Log.e("Day la CatagorryDetail", " Catagory DetailFragment")

        myListBook.clear()

        bookAdapter = BookAdapter()
        bookAdapter.setOnItemClickListener(this)

        binding.catagoryDetaiRecycleview.hasFixedSize()
        binding.catagoryDetaiRecycleview.adapter = bookAdapter

        Log.e("Day la string trong viewmodel ", viewModel.getName_of_book().value.toString())
        viewModel.getName_of_book().observe(viewLifecycleOwner, {
            Log.e("Get String", it)
            getRecycleView(it)
        })

        return binding.root
    }

    private fun getRecycleView(string: String) {

        //get data from firebase
        database.collection(string)
            .orderBy("id", Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("FireStore Error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            Log.e("Alo "," Khong nhan duoc cai gi a ?")
                            myListBook.add(dc.document.toObject(Book::class.java))
                        }
                    }

                    bookAdapter.updateList(myListBook)
                }
            })
    }

    override fun onClick(book: Book) {
        //set datafor viewmodel of catagoryDetail

        viewModel2.initBook(book)
//        //add fragment
//        activity?.supportFragmentManager?.beginTransaction()
//            ?.setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
//            ?.replace(R.id.contain_activity_layout,WordFragment())
//            ?.addToBackStack("word")
//            ?.commit()
        binding.catagoryDetaiRecycleview.visibility = View.GONE

        childFragmentManager.beginTransaction().setCustomAnimations(R.anim.left_in,R.anim.left_out,R.anim.right_in,R.anim.right_out)
            .replace(R.id.catagory_detail_layoutcontainer,WordFragment())
            .addToBackStack("word")
            .commit()
    }
}