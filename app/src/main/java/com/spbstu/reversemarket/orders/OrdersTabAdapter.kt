package com.spbstu.reversemarket.orders

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.spbstu.reversemarket.R


class OrdersTabAdapter(
    fm: FragmentManager,
    private val titles: List<String>,
    private val context: Context
) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> BoughtOrdersFragment()
            1 -> SoldOrdersFragment()
            else -> throw IllegalStateException()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> titles[0]
            1 -> {
                val title = "${titles[1]}   "
                val lastPos = title.length - 1
                val notificationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_red_notification)!!
                val sb = SpannableStringBuilder(title)


                notificationIcon.setBounds(
                    15,
                    15,
                    notificationIcon.intrinsicWidth + 15,
                    notificationIcon.intrinsicHeight + 15
                )
                val span = ImageSpan(notificationIcon, ImageSpan.ALIGN_BASELINE)
                sb.setSpan(span, lastPos - 2, lastPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                return sb
            }
            else -> throw IllegalStateException()
        }
    }


}