package com.spbstu.reversemarket.product.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
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
            frg_product_sell_button.visibility = View.INVISIBLE
        } else {
            frg_product_sell_button.visibility = View.VISIBLE
            frg_product_sell_button.setOnClickListener { view ->
                view.isClickable = false
                if (this::request.isInitialized) {
                    viewModel.buyProposal(request.bestProposal ?: return@setOnClickListener)
                        .observe(viewLifecycleOwner) {
                            view.isClickable = true
                            if (it) {
                                findNavController().popBackStack()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Не удалось купить предложение",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getProposal(request.bestProposal ?: 0, isSell)
            .observe(viewLifecycleOwner) {
                if (it != null) {
                    if (it is BestOfferViewModel.ProposalResult.Data) {
                        it.order.let {
                            layout_proposal_item__progress.visibility = View.GONE
                            layout_proposal_item__description.text = it.description
                            layout_proposal_item__price.text = it.price.toString()
                            layout_proposal_item__username.text = it.userName
                            layout_proposal_item__date.text = it.date
                            layout_proposal_item__name.text = it.name
                            layout_proposal_item__full_name.text = it.itemName
                            if (it.photos.isNotEmpty()) {
                                layout_proposal_item__main_image.isVisible = true
                                Glide.with(this)
                                    .load(NetworkModule.BASE_URL + it.photos[0])
                                    .centerCrop()
                                    .into(layout_proposal_item__main_image)
                            } else {
                                layout_proposal_item__main_image.isVisible = true
                            }
                        }
                    } else {
                        layout_proposal_item__progress.visibility = View.GONE
                    }
                } else {
                    layout_proposal_item__progress.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.no_internet_connection),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}