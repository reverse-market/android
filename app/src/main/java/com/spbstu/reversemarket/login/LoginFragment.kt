package com.spbstu.reversemarket.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spbstu.reversemarket.R

class LoginFragment : Fragment() {

    private lateinit var loginWithGoogleButton: Button
    private lateinit var loginWithFacebookButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val navView: BottomNavigationView? = activity?.findViewById(R.id.nav_view)
        if (navView != null) {
            navView.visibility = View.GONE
        }

        loginWithGoogleButton = view.findViewById(R.id.frg_login__google_login_button)
        loginWithFacebookButton = view.findViewById(R.id.frg_login__facebook_login_button)
        loginWithGoogleButton.setOnClickListener(loginWithGoogleListener)
        loginWithFacebookButton.setOnClickListener(loginWithFacebookListener)
        return view
    }

    private val loginWithGoogleListener = View.OnClickListener {
        findNavController().navigate(R.id.action_navigation_login_to_navigation_buy)
    }

    private val loginWithFacebookListener = View.OnClickListener {
        findNavController().navigate(R.id.action_navigation_login_to_navigation_buy)
    }
}