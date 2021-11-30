package com.zyp.codetest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zyp.codetest.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}