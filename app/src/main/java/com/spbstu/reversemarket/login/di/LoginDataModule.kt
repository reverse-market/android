package com.spbstu.reversemarket.login.di

import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.login.data.api.LoginApi
import com.spbstu.reversemarket.login.data.repository.LoginRepositoryImpl
import com.spbstu.reversemarket.login.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class LoginDataModule {
    companion object {
        @Provides
        @FeatureScope
        fun provideDataApi(retrofit: Retrofit): LoginApi = retrofit.create(LoginApi::class.java)

        @Provides
        @FeatureScope
        fun provideLoginRepository(loginApi: LoginApi): LoginRepository = LoginRepositoryImpl(loginApi)
    }
}