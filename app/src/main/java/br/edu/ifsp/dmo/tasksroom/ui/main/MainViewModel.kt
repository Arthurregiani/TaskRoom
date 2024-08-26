package br.edu.ifsp.dmo.tasksroom.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.dmo.tasksroom.data.model.Task
import br.edu.ifsp.dmo.tasksroom.data.repository.TaskRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: TaskRepository) : ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    init {
        checkDatabase()
    }

    fun checkDatabase() {
        viewModelScope.launch {
            val list = repository.findAll()
            _tasks.postValue(list)
        }
    }

    fun makeTaskDone(id: Long) {
        viewModelScope.launch {
            val task = repository.findById(id)
            if (task != null) {
                repository.remove(task)
                checkDatabase()
            }
        }
    }
}