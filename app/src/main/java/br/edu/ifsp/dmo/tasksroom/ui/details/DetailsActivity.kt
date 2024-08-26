package br.edu.ifsp.dmo.tasksroom.ui.details

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.tasksroom.data.repository.TaskRepository
import br.edu.ifsp.dmo.tasksroom.databinding.ActivityDatailsBinding
import br.edu.ifsp.dmo.tasksroom.util.Constant

import java.time.LocalDate
import java.time.format.DateTimeFormatter
class DetailsActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityDatailsBinding
    private lateinit var viewModel: DetailsViewModel
    private var deadLine: LocalDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = DetailsViewModelFactory(TaskRepository(applicationContext))
        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
        handleBundle()
        setupUi()
        setupListeners()
        setupObservers()
    }
    override fun onDateSet(datePicker: DatePicker, year: Int, mounth: Int, dayOfMounth:
    Int) {
        deadLine = LocalDate.of(year, mounth+1, dayOfMounth)
        setupUi()
    }
    private fun handleBundle(){
        if (intent.hasExtra(Constant.TASK_ID)){
            val id = intent.getLongExtra(Constant.TASK_ID, -1)
            viewModel.showEvent(id)
        }
    }
    private fun setupUi(){
        val str = "${deadLine.dayOfMonth}/${deadLine.monthValue}/${deadLine.year}"
        binding.buttonDeadline.text = str
    }
    private fun setupObservers() {
        viewModel.saved.observe(this, Observer {
            if (it){
                Toast.makeText(this, "Tarefa salva com sucesso.",
                    Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Erro ao salvar tarefa.", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.isUpdate.observe(this, Observer {
            if (it){
                binding.buttonSave.text = "salvar alteracao"
            }
        })
        viewModel.title.observe(this, Observer {
            binding.editTitle.setText(it)
        })
        viewModel.description.observe(this, Observer {
            binding.editDescription.setText(it)
        })
        viewModel.deadlineString.observe(this, Observer {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            deadLine = LocalDate.parse(it, formatter)
            setupUi()
        })
    }
    private fun setupListeners() {
        binding.buttonDeadline.setOnClickListener{
            initDatePickerDialog()
        }
        binding.buttonSave.setOnClickListener {
            val title = binding.editTitle.text.toString()
            val desc = binding.editDescription.text.toString()
            if (title.isEmpty() || title.isBlank()){
                Toast.makeText(this, "Título da tarefa é obrigatório",
                    Toast.LENGTH_SHORT).show()
            } else {
                viewModel.saveTask(title, desc, deadLine)
            }
        }
    }
    private fun initDatePickerDialog() {
        DatePickerDialog(this, this, deadLine.year, deadLine.monthValue-1,
            deadLine.dayOfMonth).show()
    }
}