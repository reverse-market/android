package com.spbstu.reversemarket.buy.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spbstu.reversemarket.R

class BuyInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_buy_info, container, false)
        view.findViewById<ImageView>(R.id.frg_buy_info__back_btn).setOnClickListener {
            findNavController().navigate(R.id.navigation_buy)
        }
        return view
    }

}