package com.example.lastjfproject.user

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.lastjfproject.R


data class User(
    val name: String? = null,
    val userName: String? = null,
    var userPass: String? = null,
    var email: String? = null,
    var avatarUrl: String? = "",
    var comment: ArrayList<Comment>? = null,
    var age: Int? = 0,
    var special: Boolean = false,
)

@BindingAdapter("loaduserImg")
fun loadImg(view: ImageView, string: String?) {
    if (string.isNullOrEmpty()) {
        Glide.with(view.context).load(R.drawable.ic_galerry).into(view)
    } else {
        Glide.with(view.context).load(string)
            .load(string)
            .apply(RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true))
            .placeholder(R.drawable.ic_galerry)
            .error(R.drawable.ic_galerry)
            .into(view)
    }
}

