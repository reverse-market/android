package com.spbstu.reversemarket.buy.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.address.AddressFragment
import com.spbstu.reversemarket.profile.data.model.AddressBodyWithId
import com.spbstu.reversemarket.profile.domain.model.Address

class AddressAdapter(
    initTags: List<AddressBodyWithId>,
    private val listener: (AddressBodyWithId) -> Unit,
    private val backNavigation: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var addresses: List<AddressBodyWithId> = initTags
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
            with(holder as AddressViewHolder) {
                addressName.text = addresses[position - 1].name
                address.text = getFullAddress(position - 1)
                name.text = getFullName(position - 1)
                editBtn.setOnClickListener { listener(addresses[position - 1]) }
            }
        } else {
            (holder as AddViewHolder).addBtn.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt(AddressFragment.BACK_NAVIGATION, backNavigation)
                findNavController(it).navigate(R.id.addressFragment, bundle)
            }
        }
    }

    private fun getFullAddress(position: Int): String {
        with(addresses[position]) {
            return "$city, $street, д. $number, ст. $building, кв. $apartment, $zip"
        }
    }

    private fun getFullName(position: Int): String {
        with(addresses[position]) {
            return "$lastName $firstName $fatherName"
        }
    }

    class AddressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var addressName: TextView = view.findViewById(R.id.layout_address_item__address_name)
        var editBtn: ImageView = view.findViewById(R.id.layout_address_item__edit_btn)
        var address: TextView = view.findViewById(R.id.layout_address_item__address)
        var name: TextView = view.findViewById(R.id.layout_address_item__name)
    }

    class AddViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var addBtn: ImageView = view.findViewById(R.id.layout_address__add_btn)
    }

}