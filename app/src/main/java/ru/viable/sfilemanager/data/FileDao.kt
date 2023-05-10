package ru.viable.sfilemanager.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.viable.sfilemanager.domain.FileItem

@Dao
interface FileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFile(file: FileItem)

    @Transaction
    @Query("DELETE FROM fileItem WHERE fileHash = :fileHash")
    fun deleteFile(fileHash: String)

    @Transaction
    @Query("SELECT * FROM fileitem ORDER BY fileName LIMIT :limit")
    fun getFiles(limit: Int): Flow<List<FileItem>>

    @Transaction
    @Query("SELECT * FROM fileitem ORDER BY fileName")
    fun getFiles(): Flow<List<FileItem>>
}
