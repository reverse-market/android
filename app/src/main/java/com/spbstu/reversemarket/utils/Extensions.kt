package com.spbstu.reversemarket.utils

object Extensions {
    fun Int.formatLeadingZero(): String {
        if (this.toString().length == 1) {
            return "0$this"
        }
        return this.toString()
    }
}