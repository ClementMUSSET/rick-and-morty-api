package org.mathieu.locations.details

import android.app.Application
import org.koin.core.component.inject
import org.mathieu.domain.models.character.Character

class LocationDetailsViewModel(application: Application): org.mathieu.ui.ViewModel<LocationDetailsState>(
    LocationDetailsState(),application){

    private val locationRepository: org.mathieu.domain.repositories.LocationRepository by inject()
    private val characterRepository: org.mathieu.domain.repositories.CharacterRepository by inject()

    fun init(locationId:Int)
    {
        fetchData(
            source = {
                locationRepository.getLocation(id = locationId)
            }
        ) {
            onSuccess {
                updateState { copy(location = Pair(it.name,it.type), error = null) }
                fetchResidents(it.residents)
            }

            onFailure {
                updateState { copy(error = it.toString()) }
            }
        }
    }

    private fun fetchResidents(residentsUrls: List<String>)
    {
        val residentsId = residentsUrls.map { url -> url.substringAfterLast("/").toInt() }

        fetchData(
            source = {
                residentsId.map { id -> characterRepository.getCharacter(id) }
            }
        ) {
            onSuccess {
                updateState { copy( residents = it, error = null) }
            }

            onFailure {
                updateState { copy(error = it.toString()) }
            }
        }
    }
}




data class LocationDetailsState(
    val location: Pair<String,String> = Pair("",""),
    val residents: List<Character> = emptyList(),
    val error: String? = null
)