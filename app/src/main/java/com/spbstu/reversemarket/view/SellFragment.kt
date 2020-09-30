package com.spbstu.reversemarket.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.viewmodel.SellViewModel

class SellFragment : Fragment() {

    private lateinit var sellViewModel: SellViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sellViewModel = ViewModelProvider(this).get(SellViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_buy, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        sellViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}