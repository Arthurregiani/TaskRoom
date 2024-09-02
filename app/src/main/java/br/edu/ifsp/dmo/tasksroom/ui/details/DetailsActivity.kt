package br.edu.ifsp.dmo.tasksroom.ui.details

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.tasksroom.data.repository.RegistroRepository
import br.edu.ifsp.dmo.tasksroom.databinding.ActivityDetailsBinding
import br.edu.ifsp.dmo.tasksroom.util.Constant
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: DetailsViewModel

    private val calendar: Calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = DetailsViewModelFactory(RegistroRepository(applicationContext))
        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)

        handleBundle()
        setupUi()
        setupListeners()
        setupObservers()
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
        updateDateTimeText()
    }

    override fun onTimeSet(timePicker: TimePicker, hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        updateDateTimeText()
    }

    private fun handleBundle() {
        if (intent.hasExtra(Constant.REGISTRO_ID)) {
            val id = intent.getLongExtra(Constant.REGISTRO_ID, -1)
            viewModel.showRecord(id)
        }
    }

    private fun setupUi() {
        val dateTimeString = "${dateFormat.format(calendar.time)} ${timeFormat.format(calendar.time)}"
        binding.textDatetime.text = dateTimeString
    }

    private fun setupObservers() {
        viewModel.saved.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Registro salvo com sucesso.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Erro ao salvar registro.", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.isUpdate.observe(this, Observer {
            if (it) {
                binding.buttonSave.text = "Salvar alteração"
            }
        })
        viewModel.title.observe(this, Observer {
            binding.editTitle.setText(it)
        })
        viewModel.description.observe(this, Observer {
            binding.editDescription.setText(it)
        })
        viewModel.dateTime.observe(this, Observer { dateTime ->
            val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            calendar.time = formatter.parse(dateTime) ?: Calendar.getInstance().time
            setupUi()
        })
    }

    private fun setupListeners() {
        binding.buttonDatePicker.setOnClickListener {
            DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.buttonTimePicker.setOnClickListener {
            TimePickerDialog(this, this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }
        binding.buttonSave.setOnClickListener {
            val title = binding.editTitle.text.toString()
            val desc = binding.editDescription.text.toString()
            if (title.isEmpty() || title.isBlank()) {
                Toast.makeText(this, "Título do registro é obrigatório", Toast.LENGTH_SHORT).show()
            } else {
                val dateTime = "${dateFormat.format(calendar.time)} ${timeFormat.format(calendar.time)}"
                viewModel.saveRecord(title, desc, dateTime)
            }
        }
    }

    private fun updateDateTimeText() {
        val dateTimeString = "${dateFormat.format(calendar.time)} ${timeFormat.format(calendar.time)}"
        binding.textDatetime.text = dateTimeString
    }
}
