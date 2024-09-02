package br.edu.ifsp.dmo.tasksroom.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.dmo.tasksroom.data.model.Registro
import br.edu.ifsp.dmo.tasksroom.data.repository.RegistroRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: RegistroRepository) : ViewModel() {
    private val _records = MutableLiveData<List<Registro>>()
    val records: LiveData<List<Registro>> = _records

    init {
        loadRecords()
    }

    private fun loadRecords() {
        viewModelScope.launch {
            repository.findAll().observeForever { registros ->
                _records.value = registros
            }
        }
    }

    fun deleteRecord(id: Long) {
        viewModelScope.launch {
            repository.deleteById(id)
            loadRecords()
        }
    }
}
