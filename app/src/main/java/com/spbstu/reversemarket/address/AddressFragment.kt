package com.spbstu.reversemarket.address

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import com.spbstu.reversemarket.profile.data.model.AddressBodyNoId
import com.spbstu.reversemarket.profile.data.model.AddressBodyWithId
import com.spbstu.reversemarket.profile.presentation.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_address.*

class AddressFragment : InjectionFragment<ProfileViewModel>(R.layout.fragment_address) {

    companion object {
        const val BACK_NAVIGATION = "back"
        const val ADDRESS_KEY = "ADDRESS_KEY"
    }

    private var id: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(BACK_NAVIGATION)
        view.findViewById<ImageView>(R.id.frg_address__back_btn).setOnClickListener {
            arguments?.getInt(BACK_NAVIGATION)
                ?.let { backNav -> findNavController().navigate(backNav) }
        }

        frg_address__save_btn.setOnClickListener {
            try {
                val id = id
                if (id == null) {
                    val naming = frg_address__naming.text.toString()
                    val region = frg_address__region.text.toString()
                    val city = frg_address__city.text.toString()
                    val house = frg_address__house.text.toString()
                    val building = frg_address__building.text.toString()
                    val flat = frg_address__flat.text.toString()
                    val index = frg_address__index.text.toString().toInt()
                    val name = frg_address__name.text.toString()
                    val surname = frg_address__surname.text.toString()
                    val father = frg_address__father_name.text.toString()
                    val street = frg_address__street.text.toString()
                    if (naming.isBlank() || region.isBlank() || city.isBlank() || house.isBlank()) {
                        throw IllegalArgumentException("Blank fields")
                    }

                    val address = AddressBodyNoId(
                        naming,
                        region,
                        city,
                        street,
                        house,
                        building,
                        flat,
                        index,
                        surname,
                        name,
                        father
                    )
                    viewModel.addAddress(address).observe(viewLifecycleOwner, {
                        if (it) {
                            findNavController().popBackStack()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Не удалось создать адрес",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                } else {
                    val naming = frg_address__naming.text.toString()
                    val region = frg_address__region.text.toString()
                    val city = frg_address__city.text.toString()
                    val house = frg_address__house.text.toString()
                    val building = frg_address__building.text.toString()
                    val flat = frg_address__flat.text.toString()
                    val index = frg_address__index.text.toString().toInt()
                    val name = frg_address__name.text.toString()
                    val surname = frg_address__surname.text.toString()
                    val father = frg_address__father_name.text.toString()
                    val street = frg_address__street.text.toString()
                    if (naming.isBlank() || region.isBlank() || city.isBlank() || house.isBlank()) {
                        throw IllegalArgumentException("Blank fields")
                    }

                    val address = AddressBodyWithId(
                        id,
                        naming,
                        region,
                        city,
                        street,
                        house,
                        building,
                        flat,
                        index,
                        surname,
                        name,
                        father
                    )
                    viewModel.editAddress(address).observe(viewLifecycleOwner, {
                        Log.d("WWWW", "observable = $it")
                        if (it) {
                            findNavController().popBackStack()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Не удалось изменить адрес",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
            } catch (e: Exception) {

            }
        }

        val address = arguments?.getParcelable<AddressBodyWithId>(ADDRESS_KEY)
        address?.let {
            id = it.id
            frg_address__naming.setText(it.name, TextView.BufferType.EDITABLE)
            frg_address__region.setText(it.region, TextView.BufferType.EDITABLE)
            frg_address__city.setText(it.city, TextView.BufferType.EDITABLE)
            frg_address__house.setText(it.number, TextView.BufferType.EDITABLE)
            frg_address__building.setText(it.building, TextView.BufferType.EDITABLE)
            frg_address__flat.setText(it.apartment, TextView.BufferType.EDITABLE)
            frg_address__index.setText(it.zip.toString(), TextView.BufferType.EDITABLE)
            frg_address__name.setText(it.firstName, TextView.BufferType.EDITABLE)
            frg_address__surname.setText(it.lastName, TextView.BufferType.EDITABLE)
            frg_address__father_name.setText(it.fatherName, TextView.BufferType.EDITABLE)
            frg_address__street.setText(it.street, TextView.BufferType.EDITABLE)
        }
    }
}