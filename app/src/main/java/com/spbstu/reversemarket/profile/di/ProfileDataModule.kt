package com.spbstu.reversemarket.profile.di

import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.profile.data.api.UserApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class ProfileDataModule {
    companion object {
        @Provides
        @FeatureScope
        fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)
    }
}