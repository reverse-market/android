package com.spbstu.reversemarket.di

import android.content.Context
import com.spbstu.App
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module(includes = [AndroidSupportInjectionModule::class, NavigationActivityModule::class, NetworkModule::class])
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindApplicationContext(app: App): Context
}