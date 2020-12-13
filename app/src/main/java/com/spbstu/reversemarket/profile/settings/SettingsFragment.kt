package com.spbstu.reversemarket.profile.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getAddresses().observe(viewLifecycleOwner, {
            Log.d("WWWW", "$it")
            addressList.adapter = AddressAdapter(
                it ?: emptyList(),
                ::provideAddressClickListener,
                R.id.settingsFragment
            )
        })
    }

    private fun provideAddresses(): List<Address> = listOf(
        Address(
            "Мой адрес",
            "Ленинградская область, г. Санкт-Петербург, Невский просп. д.28, кв. 1, 190000",
            "Иванов Иван Иванович"
        ),
        Address(
            "Работа",
            "Ленинградская область, г. Санкт-Петербург, Невский просп. д.35, кв. 30, 190000",
            "Иванов Иван Иванович"
        )
    )

    private fun provideAddressClickListener(address: AddressBodyWithId) {
        val bundle = Bundle()
        bundle.putParcelable(AddressFragment.ADDRESS_KEY, address)
        findNavController().navigate(R.id.addressFragment, bundle)
    }
}