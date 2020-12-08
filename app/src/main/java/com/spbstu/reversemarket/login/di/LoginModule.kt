package com.spbstu.reversemarket.login.di

import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.di.base.ViewModelKey
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.login.presentation.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [LoginDataModule::class])
abstract class LoginModule {
    @Binds
    @IntoMap
    @FeatureScope
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun viewModel(viewModel: LoginViewModel): ViewModel
}