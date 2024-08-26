package br.edu.ifsp.dmo.tasksroom.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Entity(tableName = "tb_tasks")
class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @NonNull
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "description")
    var description: String = "",
    deadLineDate: LocalDate = LocalDate.now()
) {
    @ColumnInfo(name = "deadline")
    var deadline: String = ""
    @Ignore
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    init {
        deadline = formatter.format(deadLineDate)
    }
    fun isHighPriority(): Boolean{
        val dead = LocalDate.parse(deadline, formatter)
        return dead <= LocalDate.now()
    }
}

