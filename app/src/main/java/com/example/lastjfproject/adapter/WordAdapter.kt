package com.example.lastjfproject.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lastjfproject.R
import com.example.lastjfproject.databinding.FragmentWordBinding
import com.example.lastjfproject.databinding.WordItemBinding
import com.example.lastjfproject.myInterface.Word_Click_to_RoomDB
import com.example.lastjfproject.myObject.Word

class WordAdapter: RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private var myListWord  =ArrayList<Word>()

    private lateinit var wordClickToRoomdb : Word_Click_to_RoomDB

    fun initData(arrayList: ArrayList<Word>){
        this.myListWord.clear()
        this.myListWord.addAll(arrayList)
        notifyDataSetChanged()
    }

    fun initClickWord(wordClickToRoomdb: Word_Click_to_RoomDB){
        this.wordClickToRoomdb = wordClickToRoomdb
    }

    class WordViewHolder(val binding: WordItemBinding, var clickWordtoDb:Word_Click_to_RoomDB) : RecyclerView.ViewHolder(binding.root){
        fun bind(word:Word){
            binding.word = word
            binding.tuvungNghia.text = word.imi
            binding.tuvungMaucau.text = word.maucau
            binding.tuvungName.text = word.name
            binding.tvAddToDb.setOnClickListener {
                clickWordtoDb.Click_Word_to_RoomDB(word)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
       val binding = DataBindingUtil.inflate<WordItemBinding>(LayoutInflater.from(parent.context),
           R.layout.word_item,parent,false)

        return WordViewHolder(binding,wordClickToRoomdb)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = myListWord[position]
        holder.bind(word)
    }

    override fun getItemCount() = myListWord.size


}