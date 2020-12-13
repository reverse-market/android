package com.spbstu.reversemarket.sell.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.filter.data.model.Tag
import kotlin.reflect.KFunction0


class TagsAdapter(
    initTags: List<Tag>,
    private val sellTagLayout: Int,
    private val deleteFunc: KFunction0<Unit>? = null,
    private val addFunc: ((Tag) -> Unit)? = null,
    private val context: Context? = null,
) : RecyclerView.Adapter<TagsAdapter.TagViewHolder>() {

    var tags: List<Tag> = initTags
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var isAsc = false
    var selectedItem = 0

    private var cachingVH: MutableSet<TagViewHolder> = mutableSetOf()
    private var isInit = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(sellTagLayout, parent, false)
        val holder = TagViewHolder(
            view,
            sellTagLayout
        )
        cachingVH.add(holder)
        return holder
    }

    override fun getItemCount(): Int = tags.size

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.name.text = tags[position].name
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
            holder.tagBtn?.setOnClickListener {
                rotateBtn(it)
                selectSorting(holder.background, position)
            }
            holder.background?.setOnClickListener { selectSorting(it, position) }
            if (isInit && selectedItem == position) {
                setBackground(holder.background, R.drawable.tag)
                if (isAsc)
                    rotateBtn(holder.tagBtn)
                isInit = false
            }
        }
    }

    private fun rotateBtn(it: View?) {
        val view =
            it?.findViewById<ImageView>(R.id.layout_sorting_list_item__button_image)
        if (view?.tag == "up") {
            view.animate().rotation(0f).start()
            view.tag = "down"
            isAsc = false
        } else {
            view?.animate()?.rotation(180f)?.start()
            view?.tag = "up"
            isAsc = true
        }
    }

    private fun selectSorting(view: View?, position: Int) {
        cachingVH.forEach {
            setBackground(it.background, R.drawable.not_selected_sorting)
        }
        setBackground(view, R.drawable.tag)
        selectedItem = position
    }

    private fun setBackground(view: View?, drawable: Int) {
        view?.background = ContextCompat.getDrawable(
            context!!,
            drawable
        )
    }

    class TagViewHolder(view: View, sellTagLayout: Int) : RecyclerView.ViewHolder(view) {
        var name: TextView
        var tagBtn: View? = null
        var background: View? = null

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
                    background = view.findViewById(R.id.layout_sorting_list_item__background)
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