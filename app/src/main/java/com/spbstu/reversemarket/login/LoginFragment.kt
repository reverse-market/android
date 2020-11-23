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
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    private var navigationView: BottomNavigationView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        navigationView = activity?.findViewById(R.id.nav_view)
        navigationView?.visibility = View.GONE

        ((view.findViewById(R.id.frg_login__google_login_button)) as Button).setOnClickListener(loginWithGoogleListener)
        ((view.findViewById(R.id.frg_login__facebook_login_button)) as Button).setOnClickListener(loginWithFacebookListener)
        return view
    }

    override fun onDestroy() {
        navigationView?.visibility = View.VISIBLE
        super.onDestroy()
    }

    private val loginWithGoogleListener = View.OnClickListener {
        findNavController().navigate(R.id.action_navigation_login_to_navigation_buy)
    }

    private val loginWithFacebookListener = View.OnClickListener {
        findNavController().navigate(R.id.action_navigation_login_to_navigation_buy)
    }
}