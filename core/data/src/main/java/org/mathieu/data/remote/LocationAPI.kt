package org.mathieu.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import org.mathieu.data.remote.responses.LocationResponse

/**
 * Fetch a location based on an Id from the API
 * @param id The id of the wanted location
 * @return A response containing a [LocationResponse] for the specified id
 * @throws HttpException if the request fails or if the status code is not [HttpStatusCode.OK].
 */
internal class LocationAPI(private val client: HttpClient)
{
    suspend fun getLocation(id:Int): LocationResponse? = client
        .get("location/$id")
        .accept(HttpStatusCode.OK)
        .body()
}