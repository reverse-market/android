package com.spbstu.reversemarket.di

import com.spbstu.reversemarket.NavigationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NavigationActivityModule {
    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class
        ]
    )
    abstract fun contributeMainActivity(): NavigationActivity
}