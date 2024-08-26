package br.edu.ifsp.dmo.tasksroom.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.dmo.tasksroom.data.model.Task
import br.edu.ifsp.dmo.tasksroom.data.repository.TaskRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
class DetailsViewModel(private val repository: TaskRepository): ViewModel() {
    private var taskId: Long = -1
    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> = _saved
    private val _isUpdate = MutableLiveData<Boolean>()
    val isUpdate: LiveData<Boolean> = _isUpdate
    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title
    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description
    private val _deadlineString = MutableLiveData<String>()
    val deadlineString: LiveData<String> = _deadlineString
    init {
        _isUpdate.value = false
    }
    fun saveTask(title: String, description: String, deadline: LocalDate){
        val task = Task(title = title, description = description, deadLineDate = deadline)
        if (_isUpdate.value == false) {
            viewModelScope.launch {
                _saved.value = repository.insert(task)
            }
        } else {
            task.id = taskId
            viewModelScope.launch {
                _saved.value = repository.update(task)
                taskId = -1
            }
        }
    }
    fun showEvent(id: Long) {
        viewModelScope.launch {
            val task = repository.findById(id)
            if (task != null){
                taskId = task.id
                _isUpdate.value = true
                _description.value = task.description
                _title.value = task.title
                _deadlineString.value = task.deadline
            }
        }
    }
}
