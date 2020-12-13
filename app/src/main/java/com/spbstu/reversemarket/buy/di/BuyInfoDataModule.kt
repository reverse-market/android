package com.spbstu.reversemarket.buy.di

import com.spbstu.reversemarket.buy.data.api.BuyInfoDataApi
import com.spbstu.reversemarket.di.scope.FeatureScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class BuyInfoDataModule {
    companion object {
        @Provides
        @FeatureScope
        fun provideBuyInfoApi(retrofit: Retrofit): BuyInfoDataApi = retrofit.create(BuyInfoDataApi::class.java)
    }
}