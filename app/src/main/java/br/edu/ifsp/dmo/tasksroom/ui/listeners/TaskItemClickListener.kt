package br.edu.ifsp.dmo.tasksroom.ui.listeners

interface TaskItemClickListener {
    fun clickDone(position: Int)
    fun clickOpen(position: Int)
}