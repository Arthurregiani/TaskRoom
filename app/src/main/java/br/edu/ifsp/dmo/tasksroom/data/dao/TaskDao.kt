package br.edu.ifsp.dmo.tasksroom.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifsp.dmo.tasksroom.data.model.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun create(task: Task): Long
    @Query("SELECT * FROM tb_tasks ORDER BY description")
    suspend fun getAll(): List<Task>
    @Query("SELECT * FROM tb_tasks WHERE id = :id")
    suspend fun getTask(id: Long): Task
    @Update
    suspend fun update(task: Task): Int
    @Delete
    suspend fun delete(task: Task): Int
}
