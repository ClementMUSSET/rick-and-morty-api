package org.mathieu.data.repositories

import org.mathieu.data.local.LocationLocal
import org.mathieu.data.local.objects.toModel
import org.mathieu.data.local.objects.toRealmObject
import org.mathieu.data.remote.LocationAPI
import org.mathieu.domain.models.location.Location
import org.mathieu.domain.repositories.LocationRepository

/**
 * Retrieves the location with the specified ID.
 *
 * The function follows these steps:
 * 1. Tries to fetch the location from the local storage.
 * 2. If not found locally, it fetches the location from the API.
 * 3. Upon successful API retrieval, it saves the location to local storage.
 * 4. If the location is still not found, it throws an exception.
 *
 * @param id The unique identifier of the location to retrieve.
 * @return The [Location] object representing the location details.
 * @throws Exception If the location cannot be found both locally and via the API.
 */
internal class LocationRepositoryImpl (
    private val locationApi : LocationAPI,
    private val locationLocal: LocationLocal
) : LocationRepository {

    override suspend fun getLocation(id: Int): Location =
        locationLocal.getLocation(id)?.toModel()
            ?: locationApi.getLocation(id = id)?.let { response ->
                val obj = response.toRealmObject()
                locationLocal.insert(obj)
                obj.toModel()
            }
            ?: throw Exception("Location not found")
}