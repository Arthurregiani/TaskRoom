package br.edu.ifsp.dmo.tasksroom.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.dmo.tasksroom.R
import br.edu.ifsp.dmo.tasksroom.data.model.Task
import br.edu.ifsp.dmo.tasksroom.databinding.ItemTaskBinding
import br.edu.ifsp.dmo.tasksroom.ui.listeners.TaskItemClickListener

class TaskAdapter(private val listener: TaskItemClickListener) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    // Dataset manipulada pelo adapter
    private var dataset: List<Task> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = dataset[position]
        val colorId = if (task.isHighPriority()) {
            R.color.red
        } else {
            R.color.green
        }
        val color = ContextCompat.getColor(holder.binding.root.context, colorId)
        holder.binding.textTitle.text = task.title
        holder.binding.imagePriority.setColorFilter(color)
        holder.binding.imageDone.setOnClickListener {
            listener.clickDone(position)
        }
        holder.binding.viewTask.setOnLongClickListener {
            listener.clickOpen(position)
            true
        }
    }
    override fun getItemCount(): Int {
        return dataset.size
    }
    /**
     * Recebe uma nova fonte de dados e notifica o adapter
     * que houve atualização nos dados.
     */
    fun submitDataset(data: List<Task>) {
        dataset = data
        this.notifyDataSetChanged()
    }
    fun getDatasetItem(position: Int): Task {
        return dataset[position]
    }
    // Inner class
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ItemTaskBinding = ItemTaskBinding.bind(view)
    }
}