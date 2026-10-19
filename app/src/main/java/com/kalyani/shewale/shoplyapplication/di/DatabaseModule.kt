package com.kalyani.shewale.shoplyapplication.di


import android.content.Context
import androidx.room.Room
import com.kalyani.shewale.shoplyapplication.roomdatabase.AppDatabase
import com.kalyani.shewale.shoplyapplication.roomdatabase.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "shoply.db").build()

    @Provides
    fun provideFavoriteDao(database: AppDatabase): FavoriteDao = database.favoriteDao()
}