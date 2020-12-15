package com.spbstu.reversemarket.buy.di

import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.buy.presentation.BuyInfoViewModel
import com.spbstu.reversemarket.category.di.CategoryDataModule
import com.spbstu.reversemarket.di.base.ViewModelKey
import com.spbstu.reversemarket.di.scope.FeatureScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [BuyInfoDataModule::class, CategoryDataModule::class])
abstract class BuyInfoModule {
    @Binds
    @IntoMap
    @FeatureScope
    @ViewModelKey(BuyInfoViewModel::class)
    internal abstract fun viewModel(viewModel: BuyInfoViewModel): ViewModel
}