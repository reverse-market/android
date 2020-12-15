package com.spbstu.reversemarket.sell.di

import com.spbstu.reversemarket.category.data.api.CategoryApi
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.sell.data.api.SellApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class SellDataModule {
    companion object {
        @Provides
        @FeatureScope
        fun provideDataApi(retrofit: Retrofit): SellApi = retrofit.create(SellApi::class.java)


        @Provides
        @FeatureScope
        fun provideCategoryApi(retrofit: Retrofit): CategoryApi = retrofit.create(CategoryApi::class.java)
    }
}