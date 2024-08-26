package br.edu.ifsp.dmo.tasksroom.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.tasksroom.data.repository.TaskRepository

class DetailsViewModelFactory(private val repository: TaskRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)){
            return DetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("View model desconhecido")
    }
}
