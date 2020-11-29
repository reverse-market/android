package com.spbstu.reversemarket.sell.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import kotlin.reflect.KFunction0


class TagsAdapter(
    initTags: List<String>,
    private val sellTagLayout: Int,
    private val deleteFunc: KFunction0<Unit>? = null,
    private val addFunc: ((String) -> Unit)? = null,
    private val context: Context? = null,
) : RecyclerView.Adapter<TagsAdapter.TagViewHolder>() {

    var tags: List<String> = initTags
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(sellTagLayout, parent, false)
        return TagViewHolder(
            view,
            sellTagLayout
        )
    }

    override fun getItemCount(): Int = tags.size

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.name.text = tags[position]
        if (context == null) {
            holder.tagBtn?.setOnClickListener {
                val newTags = tags.toMutableList()
                val tag = newTags[position]
                newTags.removeAt(position)
                tags = newTags
                notifyDataSetChanged()
                deleteFunc?.invoke()
                addFunc?.invoke(tag)
            }
        } else {
            holder.tagBtn?.setOnClickListener { it ->
                val view =
                    it.findViewById<ImageView>(R.id.layout_sorting_list_item__button_image)
                if (view.tag == "up") {
                    view.animate().rotation(0f).start()
                    view.tag = "down"
                } else {
                    view.animate().rotation(180f).start()
                    view.tag = "up"
                }
            }
        }
    }

    class TagViewHolder(view: View, sellTagLayout: Int) : RecyclerView.ViewHolder(view) {
        var name: TextView
        var tagBtn: View? = null

        init {
            when (sellTagLayout) {
                R.layout.layout_removable_product_tag -> {
                    tagBtn = view.findViewById(R.id.layout_removable_product_tag__btn)
                    name = view.findViewById(R.id.layout_removable_product_tag__name)
                }
                R.layout.layout_filter_selected_item -> {
                    tagBtn = view.findViewById(R.id.layout_filter_selected_item__delete_btn)
                    name = view.findViewById(R.id.layout_filter_selected_item__text)
                }
                R.layout.layout_sorting_list_item -> {
                    tagBtn = view.findViewById(R.id.layout_sorting_list_item__button)
                    name = view.findViewById(R.id.layout_sorting_list_item__name)
                }
                R.layout.layout_add_tag -> {
                    tagBtn = view.findViewById(R.id.layout_add_tag__add_btn)
                    name = view.findViewById(R.id.layout_add_tag__text)
                }
                else -> name = view.findViewById(R.id.layout_product_tag__name)
            }
        }
    }
}