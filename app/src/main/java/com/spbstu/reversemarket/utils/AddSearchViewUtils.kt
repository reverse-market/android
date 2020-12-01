package com.spbstu.reversemarket.utils

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.sell.presentation.TagsAdapter

class AddSearchViewUtils {

    companion object {
        const val NO_MARGIN_FLAG = -1

        private fun openSearchView(
            searchButtonBackground: RelativeLayout,
            searchOpenLayout: View,
            closeBtn: ImageView,
            margin: Int
        ) {
            searchButtonBackground.setBackgroundResource(R.drawable.search_background_filter)
            if (margin != NO_MARGIN_FLAG) {
                (searchButtonBackground.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = 0
            }
            searchOpenLayout.visibility = View.VISIBLE
            closeBtn.visibility = View.VISIBLE
        }

        private fun closeSearchView(
            searchButtonBackground: RelativeLayout,
            searchOpenLayout: View,
            closeBtn: ImageView,
            margin: Int
        ) {
            searchButtonBackground.setBackgroundResource(R.drawable.search_background)
            searchOpenLayout.visibility = View.GONE
            if (margin != NO_MARGIN_FLAG) {
                (searchButtonBackground.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = margin
            }
            closeBtn.visibility = View.GONE
        }

        private fun keyboardShown(rootView: View): Boolean {
            val softKeyboardHeight = 100
            val r = Rect()
            rootView.getWindowVisibleDisplayFrame(r)
            val dm = rootView.resources.displayMetrics
            val heightDiff: Int = rootView.bottom - r.bottom
            return heightDiff > softKeyboardHeight * dm.density
        }

        fun addTag(recycle: RecyclerView, tag: String) {
            val adapter = (recycle.adapter as TagsAdapter)
            val list = adapter.tags.toMutableList()
            list.add(tag)
            adapter.tags = list
            adapter.notifyDataSetChanged()
        }

        fun getFocusListener(
            search: EditText,
            searchButtonBackground: RelativeLayout,
            searchOpenLayout: View,
            closeBtn: ImageView,
            margin: Int
        ) = ViewTreeObserver.OnGlobalLayoutListener {
            if (search.isFocused) {
                if (keyboardShown(searchOpenLayout.rootView)) {
                    openSearchView(searchButtonBackground, searchOpenLayout, closeBtn, margin)
                } else {
                    closeSearchView(searchButtonBackground, searchOpenLayout, closeBtn, margin)
                }
            }
        }

    }

}