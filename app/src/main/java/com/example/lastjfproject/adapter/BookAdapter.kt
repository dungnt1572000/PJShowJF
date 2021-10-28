package com.example.lastjfproject.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lastjfproject.R
import com.example.lastjfproject.databinding.BookItemBinding
import com.example.lastjfproject.myInterface.GetClickBook
import com.example.lastjfproject.myObject.Book

class BookAdapter :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    private lateinit var onClick: GetClickBook
    private var bookItemList = ArrayList<Book>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(arrayList: ArrayList<Book>){
        bookItemList.clear()
        bookItemList.addAll(arrayList)
        this.notifyDataSetChanged()
    }

    fun initBookData(arrayList: ArrayList<Book>){
        this.bookItemList = arrayList
//        this.notifyDataSetChanged()
    }
    fun setOnItemClickListener(listener: GetClickBook){
        onClick = listener
    }
    class BookViewHolder(val binding: BookItemBinding, var listener: GetClickBook) : RecyclerView.ViewHolder(binding.root){
        fun bin(book:Book){
            binding.bookRating.rating = book.rating!!.toFloat()
            binding.bookName.text = book.name
            Glide.with(binding.imgBook.context)
                .load(book.url_Img)
                .into(binding.imgBook)
            binding.bookRating.rating = book.rating.toFloat()
            binding.cardviewBook.setOnClickListener {
                listener.onClick(book)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
       val binding:BookItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context)
           ,R.layout.book_item,parent,false)
        return BookViewHolder(binding,onClick)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        var book = bookItemList[position]
        holder.bin(book)
    }

    override fun getItemCount() = bookItemList.size
}