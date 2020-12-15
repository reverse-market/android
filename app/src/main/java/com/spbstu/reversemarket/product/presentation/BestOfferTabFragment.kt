package com.spbstu.reversemarket.product.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import com.spbstu.reversemarket.di.NetworkModule
import com.spbstu.reversemarket.sell.data.model.Request
import kotlinx.android.synthetic.main.layout_proposal_item.*

class BestOfferTabFragment(
    private val ids: IntArray?,
    private val isSell: Boolean,
    private val bundle: Bundle
) :
    InjectionFragment<BestOfferViewModel>(R.layout.layout_proposal_item) {

    private lateinit var request: Request
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        request = bundle.getParcelable(ProductFragment.REQUEST_KEY)
            ?: throw IllegalArgumentException("Request must be provided")
        if (isSell) {
            frg_product_sell_button.visibility = View.VISIBLE
        } else {
            frg_product_sell_button.visibility = View.GONE
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getProposal(request.bestProposal!!, isSell)
            .observe(viewLifecycleOwner,  {
                layout_proposal_item__description.text = it.description
                layout_proposal_item__price.text = it.price.toString()
                layout_proposal_item__username.text = it.userName
                layout_proposal_item__date.text = it.date
                layout_proposal_item__name.text = it.name
                layout_proposal_item__full_name.text = it.itemName
                if (it.photos.isNotEmpty()) {
                    Glide.with(this)
                        .load(NetworkModule.DATA_BASE_URL + it.photos[0])
                        .centerCrop()
                        .into(layout_proposal_item__main_image)
                }
            })
    }
}