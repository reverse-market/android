package com.spbstu.reversemarket.login.presentation

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject


class LoginFragment : InjectionFragment<LoginViewModel>(R.layout.fragment_login) {
    companion object {
        const val GOOGLE_RC_SIGN_IN = 9002
    }

    private var navigationView: BottomNavigationView? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationView = activity?.findViewById(R.id.nav_view)
        navigationView?.visibility = View.GONE
        ((view.findViewById(R.id.frg_login__google_login_button)) as Button).setOnClickListener(
            loginWithGoogleListener
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                LoginViewModel.State.SUCCESS -> {
                    findNavController().navigate(R.id.action_navigation_login_to_navigation_buy)
                }
                LoginViewModel.State.ERROR -> {
                    frg_login__google_login_button.isClickable = true
                }
            }
        }
    }

    override fun onDestroy() {
        navigationView?.visibility = View.VISIBLE
        super.onDestroy()
    }

    private val loginWithGoogleListener = View.OnClickListener {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        val client = GoogleSignIn.getClient(requireActivity(), gso)
        startActivityForResult(client.signInIntent, GOOGLE_RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            val idToken = account?.idToken
            viewModel.signIn(idToken!!)
        }
    }
}