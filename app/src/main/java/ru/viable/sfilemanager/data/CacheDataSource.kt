package ru.viable.sfilemanager.data

import kotlinx.coroutines.flow.Flow
import ru.viable.sfilemanager.domain.FileItem

interface CacheDataSource {

    fun getFileList(): Flow<List<FileItem>>
}
