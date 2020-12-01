package com.spbstu.reversemarket.buy.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.buy.domain.Address
import com.spbstu.reversemarket.sell.domain.model.Category

class AddressAdapter(
    initTags: List<Address>,
    private val listener: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var addresses: List<Address> = initTags
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_address_add_item, parent, false)
            AddViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_address_item, parent, false)
            AddressViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int = if (position == 0)  0 else 1

    override fun getItemCount(): Int = addresses.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 1) {
            val addressViewHolder = holder as AddressViewHolder
            addressViewHolder.addressName.text = addresses[position - 1].addressName
            addressViewHolder.address.text = addresses[position - 1].address
            addressViewHolder.name.text = addresses[position - 1].name
            addressViewHolder.editBtn.setOnClickListener { listener(position) }
        }
    }

    class AddressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var addressName: TextView = view.findViewById(R.id.layout_address_item__address_name)
        var editBtn: ImageView = view.findViewById(R.id.layout_address_item__edit_btn)
        var address: TextView = view.findViewById(R.id.layout_address_item__address)
        var name: TextView = view.findViewById(R.id.layout_address_item__name)
    }

    class AddViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

}