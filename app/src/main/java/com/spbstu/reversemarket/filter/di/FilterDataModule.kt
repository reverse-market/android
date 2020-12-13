package com.spbstu.reversemarket.filter.di

import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.filter.data.api.FilterApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class FilterDataModule {
    companion object {
        @Provides
        @FeatureScope
        fun provideDataApi(retrofit: Retrofit): FilterApi = retrofit.create(FilterApi::class.java)
    }
}