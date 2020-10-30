package com.spbstu.reversemarket.sell

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import kotlin.reflect.KFunction0

class TagsAdapter(
    var tags: List<String>,
    private val sellTagLayout: Int,
    private val filter: KFunction0<Unit>? = null
) : RecyclerView.Adapter<TagsAdapter.TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(sellTagLayout, parent, false);
        return TagViewHolder(view, sellTagLayout)
    }

    override fun getItemCount(): Int = tags.size

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.name.text = tags[position]
        holder.deleteTagBtn?.setOnClickListener {
            val newTags = tags.toMutableList()
            newTags.removeAt(position)
            tags = newTags
            notifyDataSetChanged()
            filter?.invoke()
        }
    }

    class TagViewHolder(view: View, sellTagLayout: Int): RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.tagName)
        var deleteTagBtn: ImageView? = null

        init {
            if (sellTagLayout == R.layout.sell_tag_layout) {
                deleteTagBtn = view.findViewById(R.id.delete_tag_btn)
            }
        }
    }
}