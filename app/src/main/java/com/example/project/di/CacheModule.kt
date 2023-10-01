package com.example.project.di

import android.content.Context
import com.example.project.cache.DetailCacheManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object CacheModule {

    @Provides
    fun provideDetailCacheManager(context: Context): DetailCacheManager {
        return DetailCacheManager(context)
    }
}
