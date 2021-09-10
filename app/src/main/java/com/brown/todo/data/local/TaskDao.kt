package com.brown.todo.data.local

import androidx.room.Dao
import androidx.room.Query
import com.brown.todo.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM Task")
    fun getTaskListFlow(): Flow<List<Task>>

}
