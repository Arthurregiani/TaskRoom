package br.edu.ifsp.dmo.tasksroom.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "registros")
data class Registro(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val title: String,
    val description: String,
    val dateTime: String
)
