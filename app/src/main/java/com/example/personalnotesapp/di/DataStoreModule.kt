package com.example.personalnotesapp.di

import android.content.Context
import com.example.personalnotesapp.utils.ProtoDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideProtoDataStore(
        @ApplicationContext context: Context
    ): ProtoDataStore {
        return ProtoDataStore(context)
    }
}
