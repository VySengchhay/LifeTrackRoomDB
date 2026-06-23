package com.androidapp.lifetrackroomdb.feature_task.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTask(task: TaskEntity)

    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun getTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :taskId LIMIT 1")
    suspend fun getTaskById(taskId: Int): TaskEntity

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)

    @Query(
        """
        SELECT * FROM tasks
        WHERE (:search IS NULL OR title LIKE '%' || :search || '%')
        AND (:status IS NULL OR status = :status)
        AND (:priority IS NULL OR priority = :priority)
        ORDER BY createdAt DESC
        """
    )
    fun filterTasks(
        search: String?,
        status: String?,
        priority: String?
    ): Flow<List<TaskEntity>>
}