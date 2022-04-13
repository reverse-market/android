package com.spbstu.reversemarket

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spbstu.reversemarket.di.AuthEvent
import com.spbstu.reversemarket.di.injector.Injectable
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_navigation.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


class NavigationActivity : AppCompatActivity(), Injectable, HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        setContentView(R.layout.activity_navigation)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(
            nav_view as BottomNavigationView,
            navHostFragment!!.navController
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: AuthEvent) {
        val controller = findNavController(R.id.nav_host_fragment)
        if (kotlin.runCatching { controller.getBackStackEntry(R.id.navigation_login) }.getOrNull() != null) return
        kotlin.runCatching {
            while (controller.popBackStack()) {
            }
            controller.navigate(R.id.navigation_login)
        }
    }
}