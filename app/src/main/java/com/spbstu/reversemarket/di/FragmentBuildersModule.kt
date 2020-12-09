package com.spbstu.reversemarket.di

import android.app.Activity
import com.spbstu.reversemarket.NavigationActivity
import com.spbstu.reversemarket.category.di.CategoryModule
import com.spbstu.reversemarket.category.presentation.CategoryFragment
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.login.di.LoginModule
import com.spbstu.reversemarket.login.presentation.LoginFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @Binds
    abstract fun activityContext(activity: NavigationActivity): Activity

    @FeatureScope
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun contributeLoginFragment(): LoginFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [CategoryModule::class])
    abstract fun contributeCategoryFragment(): CategoryFragment
}