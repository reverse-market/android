package com.spbstu.reversemarket.buy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.spbstu.reversemarket.R

class BuyFragment : Fragment() {

    private lateinit var buyViewModel: BuyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        buyViewModel = ViewModelProvider(this).get(BuyViewModel::class.java)

        return inflater.inflate(R.layout.fragment_filter, container, false)
    }
}