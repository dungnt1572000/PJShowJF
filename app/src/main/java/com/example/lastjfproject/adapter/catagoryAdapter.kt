package com.example.lastjfproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lastjfproject.R
import com.example.lastjfproject.myInterface.Item_catagory_clickListener
import com.example.lastjfproject.myObject.Catagory


class catagoryAdapter(var myList: ArrayList<Catagory>, private val itemCatagoryClicklistener: Item_catagory_clickListener): RecyclerView.Adapter<catagoryAdapter.catagoryViewHolder>() {

    class catagoryViewHolder(itemView: View,onClick:Item_catagory_clickListener) : RecyclerView.ViewHolder(itemView){
        var catagory_name = itemView.findViewById<TextView>(R.id.catagory_name)
        var catagory_img = itemView.findViewById<ImageView>(R.id.catagory_img)
        var catagory_cardview = itemView.findViewById<CardView>(R.id.item_catagory_container)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): catagoryViewHolder {
        val view : View =LayoutInflater.from(parent.context).inflate(R.layout.item_catagory,parent,false)
        return catagoryViewHolder(view,itemCatagoryClicklistener)
    }

    override fun onBindViewHolder(holder: catagoryViewHolder, position: Int) {
        var catagory = myList[position]
       holder.catagory_name.text = catagory.name
        Glide.with(holder.catagory_img.context).load(catagory.string_url_image).into(holder.catagory_img)
        holder.catagory_cardview.setOnClickListener {
            itemCatagoryClicklistener.ClickCatagoryListener(position)
        }
    }

    override fun getItemCount() = myList.size
}