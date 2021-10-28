package com.example.lastjfproject.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lastjfproject.R
import com.example.lastjfproject.user.Comment

class CommentAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object{
        const val VIEW_TYPE_YOUR = 1
        const val VIEW_TYPE_ANOTHER = 2
    }

    private var arrCmt = ArrayList<Comment>()

    fun initData(list:ArrayList<Comment>){
        this.arrCmt.clear()
        this.arrCmt.addAll(list)
        notifyDataSetChanged()
    }


    private inner class YourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var message = itemView.findViewById<TextView>(R.id.your_cmt)
        var img = itemView.findViewById<ImageView>(R.id.your_img)
        var name = itemView.findViewById<TextView>(R.id.your_name)
        fun bind(position: Int){
            message.text = arrCmt[position].cmt
            name.text = arrCmt[position].user!!.userName
            Glide.with(img.context).load(arrCmt[position].user?.avatarUrl).error(R.drawable.ic_galerry).placeholder(R.drawable.ic_galerry).into(img)
        }
    }
    private inner class AnotherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var message = itemView.findViewById<TextView>(R.id.another_cmt)
        var img = itemView.findViewById<ImageView>(R.id.another_img)
        var name = itemView.findViewById<TextView>(R.id.another_name)
        fun bind(position: Int){
            message.text = arrCmt[position].cmt
            name.text = arrCmt[position].user?.userName
            Glide.with(img.context).load(arrCmt[position].user?.avatarUrl).error(R.drawable.ic_galerry).placeholder(R.drawable.ic_galerry).into(img)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return arrCmt[position].viewType
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_YOUR){
            YourViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.comment_your_item,parent,false))
        }else{
            AnotherViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.comment_another_item,parent,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (arrCmt[position].viewType == VIEW_TYPE_YOUR){
            (holder as YourViewHolder).bind(position)
        }else{
            (holder as AnotherViewHolder).bind(position)
        }
    }

    override fun getItemCount() = arrCmt.size


}