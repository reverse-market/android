package com.spbstu.reversemarket.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.spbstu.reversemarket.R

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.findViewById<LinearLayout>(R.id.frg_profile__settings_button).setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        view.findViewById<LinearLayout>(R.id.frg_profile__favorite_button).setOnClickListener {
            findNavController().navigate(R.id.favoriteFragment)
        }

        return view
    }
}