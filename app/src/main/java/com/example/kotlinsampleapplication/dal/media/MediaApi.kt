package com.example.kotlinsampleapplication.dal.media

import android.content.Context
import android.util.Log
import com.example.base.Common
import com.example.base.Common.Companion.sdf
import com.example.kotlinsampleapplication.Model.MediaModel
import com.example.kotlinsampleapplication.Model.WeatherModel
import com.google.gson.Gson
import fi.iki.elonen.NanoHTTPD
import java.util.*


class MediaApi {
    val tag: String = "MediaApi"
    var context: Context? = null

    constructor(activity: Context ){
        context = activity
    }

    val videoRepo: MediaRepository by lazy {
        MediaRepository(MediaDBHelper.getDatabase(this.context).mediaDao()!!)
    }

    fun findActionMethod(url:List<String>,para:Map<String, String>): String{

        when {
            url[2].equals("getAll") -> {
                return getMediaSchedules()
            }
            url[2].equals("getType") -> {
                return getMediaSchedulesByType(para["type"]!!)
            }
            url[2].equals("getPath") -> {
                return getMediaSchedulesByPath(para["path"]!!)
            }
            url[2].equals("getFileName") -> {
                return getMediaSchedulesByFileName(para["fileName"]!!)
            }
            url[2].equals("updateMedia") -> {
                var media = Gson().fromJson(para.get("postData").toString(), MediaModel::class.java)
                return updateMediaScheduleByPara(media.path,media.type,media.fileName,sdf.parse(media.startDate), sdf.parse(media.endDate))
            }
            else -> return ""
        }
    }

    private fun getMediaSchedules(): String {
        return Gson().toJson(videoRepo.getAll())
    }

    private fun getMediaSchedulesByType(type:String): String {
        return Gson().toJson(videoRepo.getMediaByType(type))
    }

    private fun getMediaSchedulesByPath(path:String): String {
        return Gson().toJson(videoRepo.getMediaByPath(path))
    }

    private fun getMediaSchedulesByFileName(fileName:String): String {
        return Gson().toJson(videoRepo.getMediaByFileName(fileName))
    }

    private fun updateMediaScheduleByPara(path: String, type: String, fileName: String, startDate: Date, endDate: Date): String {
        return Gson().toJson(videoRepo.updateMediaSchedule(path, type, fileName, startDate, endDate))
    }
}