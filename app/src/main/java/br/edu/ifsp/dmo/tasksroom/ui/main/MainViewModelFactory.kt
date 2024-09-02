package br.edu.ifsp.dmo.tasksroom.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.tasksroom.data.repository.RegistroRepository

class MainViewModelFactory(private val repository: RegistroRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("View Model desconhecido")
    }
}
