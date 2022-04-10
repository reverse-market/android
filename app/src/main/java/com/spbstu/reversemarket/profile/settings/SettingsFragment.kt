package com.spbstu.reversemarket.profile.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.address.AddressFragment
import com.spbstu.reversemarket.base.InjectionFragment
import com.spbstu.reversemarket.buy.domain.Address
import com.spbstu.reversemarket.buy.presentation.AddressAdapter
import com.spbstu.reversemarket.profile.data.model.AddressBodyWithId
import com.spbstu.reversemarket.profile.presentation.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : InjectionFragment<ProfileViewModel>(R.layout.fragment_settings) {

    private lateinit var addressList: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settings_back_button.setOnClickListener {
            findNavController().popBackStack()
        }
        addressList = view.findViewById(R.id.layout_address_list)

        frg_settings__save_btn.setOnClickListener { view ->
            view.isClickable = false
            val newName = frg_settings__name_field.text.toString()
            if (newName.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Новое имя не должно быть пустым",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            viewModel.changeName(newName).observe(viewLifecycleOwner) {
                if (it) {
                    try {
                        view.isClickable = true
                        findNavController().popBackStack()
                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось изменить данные",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    view.isClickable = true
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.no_internet_connection),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getAddresses().observe(viewLifecycleOwner) {
            addressList.adapter = AddressAdapter(
                it,
                ::provideAddressClickListener,
                R.id.settingsFragment
            )
        }
    }

    private fun provideAddressClickListener(address: AddressBodyWithId) {
        val bundle = Bundle()
        bundle.putParcelable(AddressFragment.ADDRESS_KEY, address)
        findNavController().navigate(R.id.action_settingsFragment_to_addressFragment, bundle)
    }
}