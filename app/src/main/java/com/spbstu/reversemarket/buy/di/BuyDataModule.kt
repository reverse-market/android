package com.spbstu.reversemarket.buy.di

import com.spbstu.reversemarket.buy.data.api.BuyApi
import com.spbstu.reversemarket.di.scope.FeatureScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class BuyDataModule {
    companion object {
        @Provides
        @FeatureScope
        fun provideBuyApi(retrofit: Retrofit): BuyApi = retrofit.create(BuyApi::class.java)
    }
}