package br.edu.ifsp.dmo.tasksroom.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import br.edu.ifsp.dmo.tasksroom.data.dao.RegistroDao
import br.edu.ifsp.dmo.tasksroom.data.database.RegistroDatabase
import br.edu.ifsp.dmo.tasksroom.data.model.Registro

class RegistroRepository(context: Context) {
    private val dao: RegistroDao = RegistroDatabase.getDatabase(context).registroDao()

    suspend fun insert(registro: Registro): Boolean {
        return dao.insert(registro) > 0
    }

    suspend fun update(registro: Registro): Boolean {
        return dao.update(registro) > 0
    }

    suspend fun deleteById(id: Long) {
        dao.deleteById(id)
    }

    fun findAll(): LiveData<List<Registro>> {
        return dao.findAll()
    }

    suspend fun findById(id: Long): Registro? {
        return dao.findById(id)
    }
}
