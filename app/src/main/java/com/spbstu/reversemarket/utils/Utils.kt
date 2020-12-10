package com.spbstu.reversemarket.utils

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.filter.data.model.Tag
import com.spbstu.reversemarket.filter.presentation.FilterFragment

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

        fun changeImage(imageView: ImageView, drawableId: Int, context: Context) {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    drawableId
                )
            )
        }

        fun initPrevTags(arguments: Bundle?): MutableList<Tag> {
            val tagsId = arguments?.getIntArray(FilterFragment.FILTER_TAGS_IDS)?.toList()
            val tagsName = arguments?.getStringArray(FilterFragment.FILTER_TAGS_NAME)?.toList()
            val prevTags: MutableList<Tag> = mutableListOf()
            val bound = (tagsId?.size ?: 0) - 1
            for (i in 0..bound) {
                prevTags.add(Tag(tagsId!![i], tagsName!![i]))
            }
            return prevTags
        }

        fun provideTagsBundle(tags: List<Tag>): Bundle {
            val args = Bundle()
            val filterTagsId = tags.map { it.id }
            val filterTagsName = tags.map { it.name }
            args.putIntArray(FilterFragment.FILTER_TAGS_IDS, filterTagsId.toIntArray())
            args.putStringArray(FilterFragment.FILTER_TAGS_NAME, filterTagsName.toTypedArray())
            return args
        }
    }
}