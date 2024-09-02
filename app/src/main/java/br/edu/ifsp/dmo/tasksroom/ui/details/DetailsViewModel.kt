package br.edu.ifsp.dmo.tasksroom.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.dmo.tasksroom.data.model.Registro
import br.edu.ifsp.dmo.tasksroom.data.repository.RegistroRepository
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: RegistroRepository) : ViewModel() {

    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> get() = _saved

    private val _isUpdate = MutableLiveData<Boolean>()
    val isUpdate: LiveData<Boolean> get() = _isUpdate

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> get() = _description

    private val _dateTime = MutableLiveData<String>()
    val dateTime: LiveData<String> get() = _dateTime

    fun showRecord(id: Long) {
        viewModelScope.launch {
            val record = repository.findById(id)
            if (record == null) {
                _saved.value = false
                return@launch
            }
            _title.value = record.title
            _description.value = record.description
            _dateTime.value = record.dateTime
            _isUpdate.value = true
        }
    }

    fun saveRecord(title: String, description: String, dateTime: String) {
        viewModelScope.launch {
            val success = if (_isUpdate.value == true) {
                repository.update(Registro(id = _dateTime.value?.toLong() ?: 0, title = title, description = description, dateTime = dateTime))
            } else {
                repository.insert(Registro(title = title, description = description, dateTime = dateTime))
            }
            _saved.value = success
        }
    }
}
