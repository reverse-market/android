package com.spbstu.reversemarket.utils

import androidx.navigation.NavController

object Extensions {
    fun Int.formatLeadingZero(): String {
        if (this.toString().length == 1) {
            return "0$this"
        }
        return this.toString()
    }

    fun NavController.popBackStackAllInstances(destination: Int, inclusive: Boolean): Boolean {
        var popped: Boolean
        while (true) {
            popped = popBackStack(destination, inclusive)
            if (!popped) {
                break
            }
        }
        return popped
    }
}