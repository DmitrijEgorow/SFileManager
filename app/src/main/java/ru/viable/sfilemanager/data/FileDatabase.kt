package ru.viable.sfilemanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.viable.sfilemanager.domain.FileItem

@Database(
    entities = [
        FileItem::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class FileDatabase : RoomDatabase() {

    abstract fun fileDao(): FileDao

    companion object {
        @Volatile
        private var INSTANCE: FileDatabase? = null

        fun getDatabase(context: Context): FileDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FileDatabase::class.java,
                    "cache_files_database",
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
