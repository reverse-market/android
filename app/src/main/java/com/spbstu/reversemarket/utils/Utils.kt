package com.spbstu.reversemarket.utils

import android.view.KeyEvent
import android.view.View
import kotlin.reflect.KFunction0

class Utils(private val filterRecycler: KFunction0<Unit>) {

    val enterListener = View.OnKeyListener { v, keyCode, event ->
        if ((event.action == KeyEvent.ACTION_DOWN) &&
            (keyCode == KeyEvent.KEYCODE_ENTER)) {
            filterRecycler()
            return@OnKeyListener true
        }
        return@OnKeyListener false
    }
}