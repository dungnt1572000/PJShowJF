package com.example.lastjfproject.myObject

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class BindingUtils {
    companion object {
        @JvmStatic
        @BindingAdapter("userImg")
        fun loadImg(view: ImageView, imgUrl:String){
            Glide.with(view.context).load(imgUrl).into(view)
        }
    }
}