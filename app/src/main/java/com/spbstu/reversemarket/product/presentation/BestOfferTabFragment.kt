package com.spbstu.reversemarket.product.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import kotlinx.android.synthetic.main.layout_best_offer.*

class BestOfferTabFragment(private val ids: IntArray?, private val isSell: Boolean) :
    InjectionFragment<BestOfferViewModel>(R.layout.layout_best_offer) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_best_offer__buy__list.layoutManager = LinearLayoutManager(context)
        layout_best_offer__buy__list.adapter =
            ProposalAdapter(
                requireActivity(),
                emptyList(),
            )
        loadItems()
    }

    private fun loadItems() {
        ids?.forEach {
            viewModel.getProposal(it, isSell).observe(viewLifecycleOwner) { proposals ->
                (layout_best_offer__buy__list.adapter as ProposalAdapter).proposals = proposals
                (layout_best_offer__buy__list.adapter as ProposalAdapter).notifyDataSetChanged()
            }
        }
    }

}