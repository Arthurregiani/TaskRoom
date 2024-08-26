package br.edu.ifsp.dmo.tasksroom.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.dmo.tasksroom.data.repository.TaskRepository
import br.edu.ifsp.dmo.tasksroom.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.tasksroom.ui.adapter.TaskAdapter
import br.edu.ifsp.dmo.tasksroom.ui.details.DetailsActivity
import br.edu.ifsp.dmo.tasksroom.ui.listeners.TaskItemClickListener
import br.edu.ifsp.dmo.tasksroom.util.Constant

class MainActivity : AppCompatActivity(), TaskItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = TaskAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory = MainViewModelFactory(TaskRepository(applicationContext))
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }
    override fun onResume() {
        super.onResume()
        viewModel.checkDatabase()
    }
    override fun clickDone(position: Int) {
        val task = adapter.getDatasetItem(position)
        viewModel.makeTaskDone(task.id)
    }
    override fun clickOpen(position: Int) {
        val task = adapter.getDatasetItem(position)
        val mIntent = Intent(this, DetailsActivity::class.java)
        mIntent.putExtra(Constant.TASK_ID, task.id)
        startActivity(mIntent)
    }
    private fun setupListeners() {
        binding.buttonAdd.setOnClickListener{
            val mIntent = Intent(this, DetailsActivity::class.java)
            startActivity(mIntent)
        }
    }
    private fun setupRecyclerView() {
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter
    }
    private fun setupObservers() {
        viewModel.tasks.observe(this, Observer {
            adapter.submitDataset(it)
        })
    }
}