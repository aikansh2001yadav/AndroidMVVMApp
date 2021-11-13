package com.example.androidmvvmapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidmvvmapp.data.model.Joke

@Dao
interface JokesDao {
    /**
     * Returns all jokes from the room database
     */
    @Query("SELECT * FROM joke")
    fun getAllJokes(): List<Joke>

    /**
     * Inserts joke into the room database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJoke(joke: Joke)

    /**
     * Deletes jokes from the room database
     */
    @Query("DELETE FROM joke")
    fun deleteJokes()

    /**
     * Deletes jokes belonging to a particular category from room database
     */
    @Query("DELETE FROM joke WHERE category IN (:category)")
    fun deleteCategoryJokes(category: String)

    /**
     * Returns jokes belonging to a particular category
     */
    @Query("SELECT * FROM joke WHERE category IN (:category)")
    fun getCategoryJokes(category: String): List<Joke>
}
