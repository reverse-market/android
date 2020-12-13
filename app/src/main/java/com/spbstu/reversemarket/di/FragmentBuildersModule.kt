package com.spbstu.reversemarket.di

import android.app.Activity
import com.spbstu.reversemarket.NavigationActivity
import com.spbstu.reversemarket.address.AddressFragment
import com.spbstu.reversemarket.buy.di.BuyInfoModule
import com.spbstu.reversemarket.buy.presentation.BuyInfoFragment
import com.spbstu.reversemarket.category.di.CategoryModule
import com.spbstu.reversemarket.category.presentation.CategoryFragment
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.filter.di.FilterModule
import com.spbstu.reversemarket.filter.presentation.FilterFragment
import com.spbstu.reversemarket.login.di.LoginModule
import com.spbstu.reversemarket.login.presentation.LoginFragment
import com.spbstu.reversemarket.product.di.ProductModule
import com.spbstu.reversemarket.product.presentation.BestOfferTabFragment
import com.spbstu.reversemarket.sell.di.SellModule
import com.spbstu.reversemarket.sell.presentation.SellFragment
import com.spbstu.reversemarket.profile.di.ProfileModule
import com.spbstu.reversemarket.profile.presentation.ProfileFragment
import com.spbstu.reversemarket.profile.settings.SettingsFragment
import com.spbstu.reversemarket.sell.di.SellInfoModule
import com.spbstu.reversemarket.sell.presentation.SellInfoFragment
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
    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun contributeProfileFragment(): ProfileFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [CategoryModule::class])
    abstract fun contributeCategoryFragment(): CategoryFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [FilterModule::class])
    abstract fun contributeFilterFragment(): FilterFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [SellModule::class])
    abstract fun contributeSellFragment(): SellFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [SellInfoModule::class])
    abstract fun contributeSellInfoFragment(): SellInfoFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [ProductModule::class])
    abstract fun contributeBestOfferFragment(): BestOfferTabFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [BuyInfoModule::class])
    abstract fun contributeBuyInfoFragment(): BuyInfoFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun contributeSettings(): SettingsFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun contributeAddresses(): AddressFragment

}