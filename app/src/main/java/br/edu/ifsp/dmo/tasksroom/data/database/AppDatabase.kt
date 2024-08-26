package br.edu.ifsp.dmo.tasksroom.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.ifsp.dmo.tasksroom.data.dao.TaskDao
import br.edu.ifsp.dmo.tasksroom.data.model.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "app_tasks.db"
        private lateinit var instance: AppDatabase
        fun getInstance(context: Context): AppDatabase {
            if (!::instance.isInitialized) {
                synchronized(AppDatabase::class) {
                    instance = Room
                        .databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            DATABASE_NAME
                        )
                        .build()
                }
            }
            return instance
        }
    }
    abstract fun getTaskDao(): TaskDao
}