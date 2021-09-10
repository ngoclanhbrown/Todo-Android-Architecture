package com.brown.todo.di

import android.content.Context
import androidx.room.Room
import com.brown.todo.config.AppConfig
import com.brown.todo.data.local.TaskDao
import com.brown.todo.data.local.TodoAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Provides
    @Singleton
    fun provideTodoAppDatabase(@ApplicationContext context: Context): TodoAppDatabase {
        return Room.databaseBuilder(
            context,
            TodoAppDatabase::class.java,
            AppConfig.DATABASE_FILE_NAME
        ).build()
    }


    @Provides
    @Singleton
    fun provideTaskDao(todoAppDatabase: TodoAppDatabase): TaskDao {
        return todoAppDatabase.todoDao
    }

}
