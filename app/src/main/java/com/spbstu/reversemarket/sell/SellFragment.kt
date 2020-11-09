package com.spbstu.reversemarket.sell

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.spbstu.reversemarket.R

class SellFragment : Fragment() {

    private lateinit var sellViewModel: SellViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sellViewModel = ViewModelProvider(this).get(SellViewModel::class.java)

        return inflater.inflate(R.layout.fragment_buy, container, false)
    }
}