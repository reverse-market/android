package com.spbstu.reversemarket.profile.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : InjectionFragment<ProfileViewModel>(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        frg_profile__settings_button.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_settingsFragment)
        }

        frg_profile__favorite_button.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_favoriteFragment)
        }

        frg_profile__orders_button.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_ordersFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}