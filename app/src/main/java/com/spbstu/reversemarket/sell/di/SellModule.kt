package com.spbstu.reversemarket.sell.di

import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.di.base.ViewModelKey
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.sell.presentation.SellViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [SellDataModule::class])
abstract class SellModule {
    @Binds
    @IntoMap
    @FeatureScope
    @ViewModelKey(SellViewModel::class)
    internal abstract fun viewModel(viewModel: SellViewModel): ViewModel
}