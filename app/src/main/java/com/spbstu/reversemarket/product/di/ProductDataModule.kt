package com.spbstu.reversemarket.product.di

import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.product.data.api.ProductApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class ProductDataModule {
    companion object {
        @Provides
        @FeatureScope
        fun provideDataApi(retrofit: Retrofit): ProductApi = retrofit.create(ProductApi::class.java)
    }
}