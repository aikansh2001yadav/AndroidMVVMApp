package com.example.androidmvvmapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidmvvmapp.data.model.Joke

@Database(entities = [Joke::class], version = 1)
@TypeConverters(FlagsTypeConverter::class)
abstract class JokesDatabase : RoomDatabase() {

    abstract fun jokesDao(): JokesDao

    /**
     * Creates an instance of JokesDatabase builder
     */
    companion object {
        operator fun invoke(context: Context): JokesDatabase {
            return Room.databaseBuilder(
                context,
                JokesDatabase::class.java,
                "offline-jokes-database"
            )
                .build()
        }
    }
}