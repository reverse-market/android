package com.spbstu.reversemarket.sell.di

import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.di.base.ViewModelKey
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.sell.presentation.SellInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [SellInfoDataModule::class])
abstract class SellInfoModule {
    @Binds
    @IntoMap
    @FeatureScope
    @ViewModelKey(SellInfoViewModel::class)
    internal abstract fun viewModel(viewModel: SellInfoViewModel): ViewModel
}