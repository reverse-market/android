package com.spbstu.reversemarket.product.di

import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.di.base.ViewModelKey
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.product.presentation.BestOfferViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ProductDataModule::class])
abstract class ProductModule {
    @Binds
    @IntoMap
    @FeatureScope
    @ViewModelKey(BestOfferViewModel::class)
    internal abstract fun viewModel(viewModel: BestOfferViewModel): ViewModel
}