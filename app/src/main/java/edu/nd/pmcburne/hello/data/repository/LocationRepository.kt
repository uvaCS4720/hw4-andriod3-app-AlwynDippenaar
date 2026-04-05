package edu.nd.pmcburne.hello.data.repository

import com.google.gson.Gson
import edu.nd.pmcburne.hello.data.local.LocationDao
import edu.nd.pmcburne.hello.data.local.LocationEntity
import edu.nd.pmcburne.hello.data.local.LocationTagCrossRef
import edu.nd.pmcburne.hello.data.remote.PlacemarkDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class LocationRepository(private val dao: LocationDao) {

    suspend fun syncIfNeeded() {
        val count = withContext(Dispatchers.IO) {
            dao.countLocations()
        }

        if (count == 0) {
            // Network request on IO thread
            val json = withContext(Dispatchers.IO) {
                URL("https://www.cs.virginia.edu/~wxt4gm/placemarks.json").readText()
            }

            val places = Gson().fromJson(
                json,
                Array<PlacemarkDto>::class.java
            ).toList()

            val locations = places.map {
                LocationEntity(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    latitude = it.visual_center.latitude,
                    longitude = it.visual_center.longitude
                )
            }

            val tags = places.flatMap { place ->
                place.tag_list.map { tag ->
                    LocationTagCrossRef(place.id, tag)
                }
            }

            withContext(Dispatchers.IO) {
                dao.insertLocations(locations)
                dao.insertTags(tags)
            }
        }
    }

    suspend fun getTags(): List<String> = withContext(Dispatchers.IO) {
        dao.getAllTags()
    }

    suspend fun getLocationsByTag(tag: String) = withContext(Dispatchers.IO) {
        dao.getLocationsByTag(tag)
    }
}