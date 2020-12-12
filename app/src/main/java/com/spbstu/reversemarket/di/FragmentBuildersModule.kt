package com.spbstu.reversemarket.di

import android.app.Activity
import com.spbstu.reversemarket.NavigationActivity
import com.spbstu.reversemarket.category.di.CategoryModule
import com.spbstu.reversemarket.category.presentation.CategoryFragment
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.filter.di.FilterModule
import com.spbstu.reversemarket.filter.presentation.FilterFragment
import com.spbstu.reversemarket.login.di.LoginModule
import com.spbstu.reversemarket.login.presentation.LoginFragment
import com.spbstu.reversemarket.sell.di.SellModule
import com.spbstu.reversemarket.sell.presentation.SellFragment
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

    @FeatureScope
    @ContributesAndroidInjector(modules = [FilterModule::class])
    abstract fun contributeFilterFragment(): FilterFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [SellModule::class])
    abstract fun contributeSellFragment(): SellFragment
}