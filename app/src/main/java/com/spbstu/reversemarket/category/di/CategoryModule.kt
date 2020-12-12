package com.spbstu.reversemarket.category.di

import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.category.presentation.CategoryViewModel
import com.spbstu.reversemarket.di.base.ViewModelKey
import com.spbstu.reversemarket.di.scope.FeatureScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [CategoryDataModule::class])
abstract class CategoryModule {
    @Binds
    @IntoMap
    @FeatureScope
    @ViewModelKey(CategoryViewModel::class)
    internal abstract fun viewModel(viewModel: CategoryViewModel): ViewModel
}