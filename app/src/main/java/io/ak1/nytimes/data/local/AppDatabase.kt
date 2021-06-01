package io.ak1.nytimes.data.local


import androidx.lifecycle.LiveData
import androidx.room.*
import io.ak1.nytimes.model.Bookmarks
import io.ak1.nytimes.model.Results
import io.ak1.nytimes.utility.calenderDate

/**
 * Created by akshay on 14,November,2020
 * akshay2211@github.io
 */

//basic database creation with its Dao classes
@Database(entities = [Results::class, Bookmarks::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resultsDao(): ResultsDao
    abstract fun bookmarksDao(): BookmarksDao
}


@Dao
interface ResultsDao {

    @Query("SELECT * FROM results_table WHERE type = :type ORDER BY publishedDate DESC")
    fun storiesByType(type: String): LiveData<List<Results>>

    @Query("SELECT * FROM results_table WHERE id = :id")
    fun getStoriesById(id: Int): LiveData<Results>

    @Query("SELECT COUNT(id) FROM results_table WHERE type = :type AND entryDate >= :filterDate")
    fun getCount(type: String, filterDate: Long = calenderDate): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(images: List<Results>)

    @Query("DELETE FROM results_table WHERE type = :type")
    suspend fun deleteBySectionType(type: String)

    @Query("DELETE FROM results_table")
    suspend fun deleteTable()

}

@Dao
interface BookmarksDao {
    @Query("SELECT * FROM bookmarks_table ORDER BY id ASC")
    fun getBookmarks(): LiveData<List<Bookmarks>>

    @Query("SELECT * FROM bookmarks_table WHERE id = :id")
    fun getBookmarksById(id: Int): LiveData<Bookmarks>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: Bookmarks)

    @Query("DELETE FROM bookmarks_table WHERE title = :title")
    suspend fun deleteByTitle(title: String)

    @Query("DELETE FROM bookmarks_table")
    suspend fun deleteTable()

    @Query("SELECT EXISTS(SELECT * FROM bookmarks_table WHERE title = :title)")
    fun contains(title: String): LiveData<Boolean>
}
