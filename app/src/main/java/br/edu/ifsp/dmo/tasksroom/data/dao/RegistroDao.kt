package br.edu.ifsp.dmo.tasksroom.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifsp.dmo.tasksroom.data.model.Registro

@Dao
interface RegistroDao {
    @Insert
    suspend fun insert(registro: Registro): Long

    @Update
    suspend fun update(registro: Registro): Int

    @Query("DELETE FROM registros WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM registros")
    fun findAll(): LiveData<List<Registro>>

    @Query("SELECT * FROM registros WHERE id = :id")
    suspend fun findById(id: Long): Registro?
}
