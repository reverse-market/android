package com.spbstu.reversemarket.filter.di

import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.di.base.ViewModelKey
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.filter.presentation.FilterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [FilterDataModule::class])
abstract class FilterModule {
    @Binds
    @IntoMap
    @FeatureScope
    @ViewModelKey(FilterViewModel::class)
    internal abstract fun viewModel(viewModel: FilterViewModel): ViewModel
}