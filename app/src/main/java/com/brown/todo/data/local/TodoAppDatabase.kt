package com.brown.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brown.todo.config.AppConfig
import com.brown.todo.data.model.Task

@Database(
    entities = [Task::class],
    version = AppConfig.DATABASE_VERSION,
    exportSchema = false
)
abstract class TodoAppDatabase : RoomDatabase() {
    abstract val todoDao: TaskDao
}
