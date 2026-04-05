package edu.nd.pmcburne.hello.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocationEntity::class, LocationTagCrossRef::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}