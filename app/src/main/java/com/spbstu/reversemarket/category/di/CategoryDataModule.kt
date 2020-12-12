package com.spbstu.reversemarket.category.di

import com.spbstu.reversemarket.category.data.api.CategoryApi
import com.spbstu.reversemarket.di.scope.FeatureScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class CategoryDataModule {
    companion object {
        @Provides
        @FeatureScope
        fun provideDataApi(retrofit: Retrofit): CategoryApi = retrofit.create(CategoryApi::class.java)
    }
}