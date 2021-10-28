package com.example.lastjfproject.ui.dowloaded

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.lastjfproject.R
import com.example.lastjfproject.adapter.WordAdapter
import com.example.lastjfproject.adapter.WordLocalAdapter
import com.example.lastjfproject.databinding.DowloadedWordFragmentBinding
import com.example.lastjfproject.myInterface.Clear_Local_Word
import com.example.lastjfproject.myObject.Word
import com.example.lastjfproject.roomDB.roomDBLocal

class DowloadedWordFragment : Fragment(), Clear_Local_Word {

    companion object {
        fun newInstance() = DowloadedWordFragment()
    }

    private lateinit var wordLocalAdapter: WordLocalAdapter

    private lateinit var viewModel: DowloadedWordViewModel

    private lateinit var binding: DowloadedWordFragmentBinding

    private  var arrTuvung = ArrayList<Word>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.dowloaded_word_fragment, container, false)

        wordLocalAdapter = WordLocalAdapter().apply {
            roomDBLocal.getInstance(requireContext())?.myWordDAO()?.getAllword()?.let {
                arrTuvung = it as ArrayList<Word>
                this.setData(it)
                this.setonclicktoclear(this@DowloadedWordFragment)
            }
        }

        binding.recycleViewDownloadedData.adapter = wordLocalAdapter

        binding.edtSearchText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                findWord_and_addtoRecycle(p0)
            }

        })

        return binding.root
    }

    private fun findWord_and_addtoRecycle(p0: Editable?) {
        val newWordArrayList = ArrayList<Word>()

        if (p0.isNullOrEmpty()){
            newWordArrayList.clear()
            wordLocalAdapter.setData(arrTuvung)
        }
        else {
            newWordArrayList.clear()
            for (arr in arrTuvung) {
                if (p0.let { arr.imi?.contains(it) } == true) {
                    newWordArrayList.add(arr)
                }
                if (p0.let { arr.name.contains(it) }){
                    newWordArrayList.add(arr)
                }
            }

            wordLocalAdapter.setData(newWordArrayList)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DowloadedWordViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun clear_word(word: Word, position: Int) {
        context?.let {
            roomDBLocal.getInstance(it)?.myWordDAO()?.deleteTuvung(word)
            arrTuvung.remove(word)
        }
        wordLocalAdapter.notifyItemRemoved(position)
        wordLocalAdapter.notifyItemRangeChanged(position,arrTuvung.size)
    }

}