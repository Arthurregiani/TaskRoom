package br.edu.ifsp.dmo.tasksroom.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.dmo.tasksroom.data.repository.RegistroRepository
import br.edu.ifsp.dmo.tasksroom.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.tasksroom.ui.adapter.RegistroAdapter
import br.edu.ifsp.dmo.tasksroom.ui.details.DetailsActivity
import br.edu.ifsp.dmo.tasksroom.ui.listeners.RegistroItemClickListener
import br.edu.ifsp.dmo.tasksroom.util.Constant

class MainActivity : AppCompatActivity(), RegistroItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = RegistroAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainViewModelFactory(RegistroRepository(applicationContext))
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        setupRecyclerView()
        setupListeners()
        setupObservers()
    }

    private fun setupRecyclerView() {
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter
    }

    private fun setupListeners() {
        binding.buttonAdd.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupObservers() {
        viewModel.records.observe(this, Observer {
            adapter.submitDataset(it)
        })
    }

    override fun clickDone(position: Int) {
        val registro = adapter.getDatasetItem(position)
        viewModel.deleteRecord(registro.id)
    }

    override fun clickOpen(position: Int) {
        val registro = adapter.getDatasetItem(position)
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constant.REGISTRO_ID, registro.id)
        startActivity(intent)
    }
}
