package br.edu.ifsp.dmo.tasksroom.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.ifsp.dmo.tasksroom.data.dao.RegistroDao
import br.edu.ifsp.dmo.tasksroom.data.model.Registro

@Database(entities = [Registro::class], version = 1, exportSchema = false)
abstract class RegistroDatabase : RoomDatabase() {
    abstract fun registroDao(): RegistroDao

    companion object {
        @Volatile
        private var INSTANCE: RegistroDatabase? = null

        fun getDatabase(context: Context): RegistroDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RegistroDatabase::class.java,
                    "registro_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
