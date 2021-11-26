package com.example.kotlinsampleapplication.dal.media

import android.util.Log
import androidx.annotation.WorkerThread
import java.util.*

class MediaRepository(val mediaDao:MediaDao) {
    val tag: String = "MediaRepository"

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insertItem(media: MediaEntity) {
        mediaDao.insert(media)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insertAll(medias: List<MediaEntity>) {
        mediaDao.insertAll(medias)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun deleteAll() {
        mediaDao.deleteAll()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun delete(media: MediaEntity) {
        mediaDao.delete(media)
    }

    fun getAll(): List<MediaEntity>{
        return mediaDao.getAll()
    }

    fun getMediaByType(type: String): List<MediaEntity>{
        return mediaDao.getMediaByVariable("%", "%", type)
    }

    fun getMediaByPath(path: String): MediaEntity? {
        var result = mediaDao.getMediaByVariable(path, "%", "%")
        if (result.isNotEmpty()) return result[0]
        return null
    }

    fun getMediaByFileName(fileName: String): MediaEntity? {
        var result = mediaDao.getMediaByVariable("%", fileName, "%")
        if (result.isNotEmpty()) return result[0]
        return null
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun updateMediaSchedule(path: String, type: String, fileName: String, startDate: Date, endDate: Date): String {
        var medias = mediaDao.getMediaByVariable("%", fileName, "%")
        if (medias.isNotEmpty()) {
            medias[0].path = path
            medias[0].type = type
            medias[0].startDate = startDate
            medias[0].endDate = endDate
            mediaDao.update(medias[0])

            return checkUpdateDB(MediaEntity(fileName,type,path,startDate,endDate))
        }
        return "find not media :${fileName}"
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun deleteMediaSchedule(fileName: String): String {
        var medias = mediaDao.getMediaByVariable("%", fileName, "%")
        if (medias.isNotEmpty()) {
            mediaDao.delete(medias[0])
            Log.i(tag, "dao delete")
            return checkDeleteDB(fileName)
        }
        return "find not media :${fileName}"
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun update(media: MediaEntity): String {
        var result = mediaDao.update(media)
        return ""
    }

    private fun checkUpdateDB(changeMedia: MediaEntity): String {
        var mediasUpdate = mediaDao.getMediaByVariable("%", changeMedia.fileName, "%")
        if (mediasUpdate.isNotEmpty() && mediasUpdate[0].path == changeMedia.path && mediasUpdate[0].type == changeMedia.type &&
            mediasUpdate[0].startDate == changeMedia.startDate && mediasUpdate[0].endDate == changeMedia.endDate)
            return "success"

        return "error"
    }

    private fun checkDeleteDB(fileName: String): String {
        var mediasUpdate = mediaDao.getMediaByVariable("%", fileName, "%")

        if (mediasUpdate.isEmpty()) return "success"
        return "error"
    }
}