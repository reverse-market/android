package com.spbstu.reversemarket.buy.di

import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.buy.presentation.BuyViewModel
import com.spbstu.reversemarket.di.base.ViewModelKey
import com.spbstu.reversemarket.di.scope.FeatureScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [BuyDataModule::class])
abstract class BuyModule {
    @Binds
    @IntoMap
    @FeatureScope
    @ViewModelKey(BuyViewModel::class)
    internal abstract fun viewModel(viewModel: BuyViewModel): ViewModel
}