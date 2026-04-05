package edu.nd.pmcburne.hello.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<LocationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTags(tags: List<LocationTagCrossRef>)

    @Query("""
        SELECT * FROM locations
        INNER JOIN LocationTagCrossRef
        ON locations.id = LocationTagCrossRef.locationId
        WHERE tag = :tag
    """)
    suspend fun getLocationsByTag(tag: String): List<LocationEntity>

    @Query("SELECT DISTINCT tag FROM LocationTagCrossRef ORDER BY tag ASC")
    suspend fun getAllTags(): List<String>

    @Query("SELECT COUNT(*) FROM locations")
    suspend fun countLocations(): Int
}