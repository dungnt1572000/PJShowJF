package com.example.lastjfproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lastjfproject.R
import com.example.lastjfproject.databinding.ItemWordLocalBinding
import com.example.lastjfproject.myInterface.Clear_Local_Word
import com.example.lastjfproject.myObject.Word

class WordLocalAdapter: RecyclerView.Adapter<WordLocalAdapter.WordLocalViewHolder>() {

    private var arrTuvung = ArrayList<Word>()

    private lateinit var click_toclear_word: Clear_Local_Word

    fun setonclicktoclear(clearTuvung: Clear_Local_Word){
        this.click_toclear_word = clearTuvung
    }
    fun setData(arrayList: ArrayList<Word>){
        this.arrTuvung = arrayList
        this.notifyDataSetChanged()
    }

    class WordLocalViewHolder(val binding: ItemWordLocalBinding , val click_to_clear: Clear_Local_Word) : RecyclerView.ViewHolder(binding.root){
        fun bind(word: Word){
            binding.tuvung = word
            binding.imgCleartuvung.setOnClickListener {
                click_to_clear.clear_word(word,adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordLocalViewHolder {
        val binding =DataBindingUtil.inflate<ItemWordLocalBinding>(LayoutInflater.from(parent.context),
            R.layout.item_word_local,parent,false)

        return WordLocalViewHolder(binding,click_toclear_word)
    }

    override fun onBindViewHolder(holder: WordLocalViewHolder, position: Int) {
        holder.bind(arrTuvung[position])
    }

    override fun getItemCount() = arrTuvung.size


}