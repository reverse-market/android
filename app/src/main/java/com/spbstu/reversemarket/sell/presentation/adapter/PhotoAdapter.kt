package com.spbstu.reversemarket.sell.presentation.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.utils.Utils

class PhotoAdapter(
    initTags: List<String>,
    private val context: Context,
    private val glide: RequestManager,
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    var urls: List<String> = initTags
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_address_add_item, parent, false)
        return PhotoViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = urls.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        if ("null" == urls[position]) {
            Utils.changeImage(holder.photo, R.drawable.ic_add_image, context)
        } else {
            glide.load(urls[position]).into(holder.photo)
        }
    }

    class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var photo: ImageView = view.findViewById(R.id.layout_address__add_btn)
    }

}