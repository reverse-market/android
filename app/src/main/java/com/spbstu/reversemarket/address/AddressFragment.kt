package com.spbstu.reversemarket.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spbstu.reversemarket.R

class AddressFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_address, container, false)
        arguments?.getInt(BACK_NAVIGATION)
        view.findViewById<ImageView>(R.id.frg_address__back_btn).setOnClickListener {
            arguments?.getInt(BACK_NAVIGATION)?.let {backNav -> findNavController().navigate(backNav) }
        }
        return view
    }

    companion object {
        const val BACK_NAVIGATION = "back";
    }
}