package com.spbstu.reversemarket.sell.di

import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.sell.data.api.SellInfoApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class SellInfoDataModule {
    companion object {
        @Provides
        @FeatureScope
        fun provideDataApi(retrofit: Retrofit): SellInfoApi = retrofit.create(SellInfoApi::class.java)
    }
}