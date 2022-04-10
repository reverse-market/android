package com.spbstu.reversemarket.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.di.NetworkModule

class FullPhotoAdapter(
    initTags: List<String>,
    private val context: Context,
    private val glide: RequestManager,
    private val onClick: (() -> Unit)? = null
) : RecyclerView.Adapter<FullPhotoAdapter.PhotoViewHolder>() {

    var urls: List<String> = initTags
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_full_photo, parent, false)
        return PhotoViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = urls.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        if ("null" == urls[position]) {
            Utils.changeImage(holder.photo, R.drawable.ic_add_image, context)
        } else {
            glide.load(NetworkModule.BASE_URL + urls[position]).centerCrop().placeholder(
                ColorDrawable(
                    Color.GRAY
                )
            ).into(holder.photo)
        }

        holder.itemView.setOnClickListener {
            onClick?.invoke()
        }
    }

    class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var photo: ImageView = view.findViewById(R.id.layout_full_photo__photo)
    }

}