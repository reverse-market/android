package com.spbstu.reversemarket.utils

import android.app.Activity
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.spbstu.reversemarket.R

class Utils(private val filterRecycler: () -> Unit) {

    val enterListener = View.OnKeyListener { v, keyCode, event ->
        if ((event.action == KeyEvent.ACTION_DOWN) &&
            (keyCode == KeyEvent.KEYCODE_ENTER)) {
            filterRecycler()
            return@OnKeyListener true
        }
        return@OnKeyListener false
    }

    companion object {
        fun closeKeyboard(activity: FragmentActivity?, editText: EditText) {
            val imm =
                activity?.applicationContext!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
        }

        private fun openKeyboard(activity: FragmentActivity?) {
            val imm =
                activity?.applicationContext!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        fun showSearchView(titleTextView: TextView,
                                   searchTextBackground: RelativeLayout,
                                   searchText: EditText,
                                   searchCloseBtn: ImageView,
                                   activity: FragmentActivity?) {
            titleTextView.visibility = View.GONE
            searchTextBackground.visibility = View.VISIBLE
            val anim = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.scale_search)
            searchTextBackground.startAnimation(anim)
            openKeyboard(activity);
            searchText.requestFocus()
            searchCloseBtn.visibility = View.VISIBLE
        }

        fun closeSearchView(titleTextView: TextView,
                            searchTextBackground: RelativeLayout,
                            searchCloseBtn: ImageView,
                            searchText: EditText,
                            activity: FragmentActivity?) {
            searchTextBackground.visibility = View.GONE
            titleTextView.visibility = View.VISIBLE
            searchCloseBtn.visibility = View.GONE
            closeKeyboard(activity, searchText)
        }
    }
}