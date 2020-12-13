package com.spbstu.reversemarket.product.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.product.data.model.Proposal


class ProposalAdapter(
    initRequests: List<Proposal>,
) : RecyclerView.Adapter<ProposalAdapter.ProposalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProposalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_proposal_item, parent, false)
        return ProposalViewHolder(
            view
        )
    }

    var proposals: List<Proposal> = initRequests
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount(): Int = proposals.size


    override fun onBindViewHolder(holder: ProposalViewHolder, position: Int) {
        with(holder) {
            productDescription.text = proposals[position].description
            price.text = proposals[position].price.toString()
            userName.text = proposals[position].userName
            date.text = proposals[position].date
        }
    }

    class ProposalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productDescription: TextView = view.findViewById(R.id.layout_proposal_item__description)
        val price: TextView = view.findViewById(R.id.layout_proposal_item__price)
        val userName: TextView = view.findViewById(R.id.layout_proposal_item__username)
        val date: TextView = view.findViewById(R.id.layout_proposal_item__date)
    }
}