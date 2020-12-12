package com.spbstu.reversemarket.profile.di

import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.di.base.ViewModelKey
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.profile.presentation.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ProfileDataModule::class])
abstract class ProfileModule {
    @Binds
    @IntoMap
    @FeatureScope
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun viewModel(viewModel: ProfileViewModel): ViewModel
}