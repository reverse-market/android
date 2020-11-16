package com.spbstu.reversemarket.sell.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import kotlin.reflect.KFunction0

class TagsAdapter(
    var tags: List<String>,
    private val sellTagLayout: Int,
    private val func: KFunction0<Unit>? = null,
    private val anim: Animation? = null,
    private val context: Context? = null,
) : RecyclerView.Adapter<TagsAdapter.TagViewHolder>() {

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
        if (anim == null) {
            holder.tagBtn?.setOnClickListener {
                val newTags = tags.toMutableList()
                newTags.removeAt(position)
                tags = newTags
                notifyDataSetChanged()
                func?.invoke()
            }
        } else {
            holder.tagBtn?.setOnClickListener { it ->
                anim.isFillEnabled = true;
                anim.fillAfter = true;
                it.startAnimation(anim)
                anim.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        val view =
                            it.findViewById<ImageView>(R.id.layout_sorting_list_item__button_image)
                        if (view.tag == "down") {
                            view.setImageResource(R.drawable.ic_up_arrow)
                            view.tag = "up"
                        } else {
                            view.setImageResource(R.drawable.ic_down_arrow)
                            view.tag = "down"
                        }
                    }

                    override fun onAnimationStart(p0: Animation?) {
                    }

                })
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
                else -> name = view.findViewById(R.id.layout_product_tag__name)
            }
        }
    }
}