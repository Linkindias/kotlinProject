package com.example.kotlinsampleapplication.dal.media

import androidx.room.*
import java.util.*

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videos: List<MediaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //新增物件時和舊物件發生衝突後的處置 REPLACE 蓋掉、ROLLBACK 閃退、ABORT 閃退 (默認)、FAIL 閃退、IGNORE 忽略，還是舊的資料
    fun insert(video: MediaEntity)

    @Query("SELECT * FROM mediaInfo WHERE path Like '%' || :path || '%' and fileName Like '%' || :fileName || '%' and type Like '%' || :type || '%'")
    fun getMediaByVariable(path: String, fileName: String, type: String): List<MediaEntity>

    @Query("SELECT * FROM mediaInfo")
    fun getAll(): List<MediaEntity>

    @Query("DELETE FROM mediaInfo")
    fun deleteAll()

    @Delete
    fun delete(video: MediaEntity)

//    @Update("UPDATE mediaInfo SET path =:path, type =:type WHERE fileName =:fileName and startDate =:startDate and endDate =:endDate")
//    fun update(path: String, fileName: String, type: String, startDate: Date, endDate: Date)

    @Update
    fun update(video: MediaEntity)
}