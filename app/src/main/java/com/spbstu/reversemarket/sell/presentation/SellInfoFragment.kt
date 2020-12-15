package com.spbstu.reversemarket.sell.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import com.spbstu.reversemarket.buy.presentation.AddressAdapter
import com.spbstu.reversemarket.buy.presentation.BuyInfoFragment
import com.spbstu.reversemarket.product.presentation.ProductFragment.Companion.PRODUCT_ID
import com.spbstu.reversemarket.profile.data.model.AddressBodyWithId
import com.spbstu.reversemarket.sell.data.model.ProposalBody
import com.spbstu.reversemarket.sell.presentation.adapter.PhotoAdapter
import com.spbstu.reversemarket.utils.Extensions.formatLeadingZero
import com.spbstu.reversemarket.utils.PermissionUtils
import kotlinx.android.synthetic.main.fragment_sell_info.*
import kotlinx.android.synthetic.main.layout_address.*
import kotlinx.android.synthetic.main.layout_new_product.*
import kotlinx.android.synthetic.main.layout_photos.*
import java.io.File
import java.util.*

class SellInfoFragment : InjectionFragment<SellInfoViewModel>(R.layout.fragment_sell_info) {

    companion object {
        const val PICK_IMAGE = 50123
    }

    private var attachedImageFile: File? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_new_product__photo_list.adapter = PhotoAdapter(
            provideUrlList(),
            requireContext(),
            Glide.with(this)
        ) {
            PermissionUtils.checkReadWriteStorage(requireContext()) {
                if (it) {
                    val intent = Intent(
                        Intent.ACTION_GET_CONTENT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    this.attachedImageFile =
                        File.createTempFile(
                            System.currentTimeMillis().toString(),
                            ".jpg",
                            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                        )
                    intent.type = "image/*"
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                    startActivityForResult(
                        Intent.createChooser(intent, getString(R.string.choose_photo)),
                        BuyInfoFragment.PICK_IMAGE
                    )
                }
            }
        }

        layout_address_list.visibility = View.GONE
        layout_address_list.adapter = AddressAdapter(
            emptyList(),
            ::provideAddressClickListener,
            R.id.sellInfoFragment
        )

        (view.findViewById(R.id.frg_sell_info__back_btn) as ImageView).setOnClickListener {
            findNavController().navigateUp()
        }

        frg_sell_info__save_btn.setOnClickListener {
            it.isClickable = false
            saveProposal(it)
        }
        layout_new_product__name.visibility = View.GONE
        layout_new_product__itemName.visibility = View.GONE
        layout_new_product__name_title.visibility = View.INVISIBLE
        layout_new_product__itemName_title.visibility = View.INVISIBLE
        layout_address__title.visibility = View.INVISIBLE
    }

    private fun saveProposal(view: View) {
        try {
            val description = layout_new_product__description_text.text.toString()
            val price = layout_new_product__price.text.toString().toInt()
            val amount = layout_new_product__amount.text.toString().toInt()
            if (description.isBlank() || price.toString().isBlank() || amount.toString()
                    .isBlank()
            ) {
                throw IllegalArgumentException("Fields must not be blank!")
            }
            val cal = Calendar.getInstance()
            cal.time = Date()
            val date = "${cal.get(Calendar.DAY_OF_MONTH).formatLeadingZero()}.${
                cal.get(Calendar.MONTH).formatLeadingZero()
            }.${cal.get(Calendar.YEAR)}"
            val proposalBody =
                ProposalBody(
                    requireArguments().getInt(PRODUCT_ID),
                    description,
                    emptyList(),
                    price,
                    amount,
                    date
                )
            viewModel.createProposal(proposalBody).observe(viewLifecycleOwner, {
                view.isClickable = true
                if (it) {
                    try {
                        findNavController().popBackStack()
                    } catch (ignored: Exception) {
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Не удалось создать объявление",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Неверно заполнены поля!", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE) {
            val uri = data?.data
            if (uri != null) {
                val file = attachedImageFile ?: return
                viewModel.copyUriDataToFile(requireActivity().contentResolver, uri, file)
                viewModel.addImageFile(uri, file)
                val adapter = layout_new_product__photo_list.adapter as PhotoAdapter
                val newList = mutableListOf<String>()
                newList.addAll(adapter.urls)
                newList.add(uri.toString())
                adapter.urls = newList
            }
        }
    }

    private fun provideUrlList(): List<String> = listOf("null")


    private fun provideAddressClickListener(address: AddressBodyWithId) {

    }

}