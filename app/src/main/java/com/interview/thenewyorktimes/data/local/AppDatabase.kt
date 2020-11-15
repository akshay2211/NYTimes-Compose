package com.interview.thenewyorktimes.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.interview.thenewyorktimes.model.Results

/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */

@Database(entities = [Results::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resultsDao(): ResultsDao
    //abstract fun commentsDao(): CommentsDao
}


@Dao
interface ResultsDao {

    @Query("SELECT * FROM results_table WHERE type = :type ORDER BY id ASC")
    fun storiesByType(type: String): LiveData<List<Results>>

    @Query("SELECT COUNT(id) FROM results_table WHERE type = :type")
    fun getCount(type: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(images: Results)

    @Query("DELETE FROM results_table WHERE type = :type")
    fun deleteBySectionType(type: String)
    /*
     @Query("SELECT MAX(indexInResponse) FROM images_table WHERE search_content = :search_content")
     fun getNextIndexInSearch(search_content: String): Int


     @Query("SELECT MAX(pageNumber) FROM images_table WHERE search_content = :search_content")
     fun getNextPageInSearch(search_content: String): Int

     @Query("DELETE FROM images_table WHERE search_content = :search_content")
     fun deleteBySearchContents(search_content: String)

     @Query("DELETE FROM images_table")
     suspend fun deleteTable()
 */

}
