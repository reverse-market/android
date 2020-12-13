package com.spbstu.reversemarket.sell.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import com.spbstu.reversemarket.buy.presentation.AddressAdapter
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_ID
import com.spbstu.reversemarket.sell.data.model.ProposalBody
import com.spbstu.reversemarket.sell.presentation.adapter.PhotoAdapter
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_sell_info.*
import kotlinx.android.synthetic.main.layout_address.*
import kotlinx.android.synthetic.main.layout_new_product.*
import kotlinx.android.synthetic.main.layout_photos.*

class SellInfoFragment : InjectionFragment<SellInfoViewModel>(R.layout.fragment_sell_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_new_product__photo_list.adapter = PhotoAdapter(
            provideUrlList(),
            requireContext(),
            Glide.with(this)
        )

        layout_address_list.adapter = AddressAdapter(
            emptyList(),
            ::provideAddressClickListener,
            R.id.sellInfoFragment
        )

        (view.findViewById(R.id.frg_sell_info__back_btn) as ImageView).setOnClickListener {
            findNavController().navigateUp()
        }

        frg_sell_info__save_btn.setOnClickListener { saveProposal() }
        viewAddress()
    }

    private fun viewAddress() {
        viewModel.getAddress().observe(viewLifecycleOwner) {
            (frg_category__list.adapter as AddressAdapter).addresses = it
        }
    }

    private fun saveProposal() {
        val price = layout_new_product__price.text.toString()
        val amount = layout_new_product__amount.text.toString()
        viewModel.addProposal(
            ProposalBody(
                requireArguments().getInt(PRODUCT_ID),
                layout_new_product__description_text.text.toString(),
                emptyList(),
                if (price.isEmpty()) 0 else price.toInt(),
                if (amount.isEmpty()) 0 else amount.toInt()
            )
        )
        findNavController().navigate(R.id.navigation_sell)
    }

    private fun provideUrlList(): List<String> = listOf("null")


    private fun provideAddressClickListener(position: Int) {

    }

}