package com.spbstu.reversemarket.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.slider.RangeSlider
import com.spbstu.reversemarket.R

class FilterFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var slider: RangeSlider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_filter, container, false)
        slider = view.findViewById(R.id.rangeSlider)
        slider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                val values = slider.values
                Log.d("onStartTouch From", values[0].toString())
                Log.d("onStartTouch T0", values[1].toString())
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = slider.values
                Log.d("onStopTouch From", values[0].toString())
                Log.d("onStopTouch T0", values[1].toString())
            }
        })
        return view
    }
}