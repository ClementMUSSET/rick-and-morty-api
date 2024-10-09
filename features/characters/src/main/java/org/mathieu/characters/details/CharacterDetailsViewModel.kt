package org.mathieu.characters.details

import android.app.Application
import org.koin.core.component.inject
import org.mathieu.domain.models.location.toLocationPreview


class CharacterDetailsViewModel(application: Application) : org.mathieu.ui.ViewModel<CharacterDetailsState>(
    CharacterDetailsState(), application) {

    private val characterRepository: org.mathieu.domain.repositories.CharacterRepository by inject()
    private val locationRepository: org.mathieu.domain.repositories.LocationRepository by inject()

    fun init(characterId: Int) {
        fetchData(
            source = {
                characterRepository.getCharacter(id = characterId)
            }
        ) {

            onSuccess {
                updateState { copy(avatarUrl = it.avatarUrl, name = it.name,location = it.location, error = null) }

                val locationId = it.location.second

                fetchLocation(locationId)
            }

            onFailure {
                updateState { copy(error = it.toString()) }
            }

            updateState { copy(isLoading = false) }
        }
    }

    private fun fetchLocation(id:Int)
    {
        fetchData(
            source = { locationRepository.getLocation(id = id) }
        ) {
            onSuccess { location ->
                val locationPreview = location.toLocationPreview()
                updateState { copy(
                    locationType = locationPreview.type ,
                    location = Pair(locationPreview.name,locationPreview.id),
                    error = null) }
            }

            onFailure {
                updateState { copy(error = it.toString()) }
            }
        }
    }
}



data class CharacterDetailsState(
    val isLoading: Boolean = true,
    val avatarUrl: String = "",
    val name: String = "",
    val location: Pair<String,Int> =Pair("",0),
    val locationType:String = "",
    val error: String? = null
)