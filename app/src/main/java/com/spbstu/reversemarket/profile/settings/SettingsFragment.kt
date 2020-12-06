package com.spbstu.reversemarket.profile.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.buy.domain.Address
import com.spbstu.reversemarket.buy.presentation.AddressAdapter
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private lateinit var addressList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        addressList = view.findViewById(R.id.layout_address_list)
        addressList.adapter = AddressAdapter(
            provideAddresses(),
            ::provideAddressClickListener
        )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settings_back_button.setOnClickListener {
            findNavController().popBackStack()
        }
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

    private fun provideAddressClickListener(position: Int) {

    }
}