package org.mathieu.data.local.objects

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mathieu.data.remote.responses.LocationResponse
import org.mathieu.domain.models.location.Location

/**
 * Represents a location entity stored in the SQLite database. This object provides fields
 * necessary to represent all the attributes of a character from the data source.
 * The object is specifically tailored for SQLite storage using Realm.
 *
 * @property id Unique identifier of the location
 * @property name Name of the location
 * @property type The type of the location
 * @property dimension The dimension in witch the location is
 * @property residents All the character who are present in this location
 * @property url The endpoint for the location
 * @property created Timestamp indicating when the location entity was created in the database.
 */
internal class LocationObject : RealmObject {
    @PrimaryKey
    var id: Int = -1
    var name: String = ""
    var type: String = ""
    var dimension: String = ""
    var residents : String = ""
    var url: String = ""
    var created: String = ""
}

internal fun LocationResponse.toRealmObject() = LocationObject().also { obj ->
    obj.id = id
    obj.name = name
    obj.type = type
    obj.dimension = dimension
    obj.residents = residents.joinToString(separator = ",")
    obj.url = url
    obj.created = created
}

internal fun LocationObject.toModel() = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents.split(",")
)