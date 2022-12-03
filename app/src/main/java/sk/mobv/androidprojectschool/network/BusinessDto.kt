package sk.mobv.androidprojectschool.network

import com.squareup.moshi.Json
import sk.mobv.androidprojectschool.database.DatabaseBusiness
import sk.mobv.androidprojectschool.domain.Business


data class BusinessContainerDto(@Json(name = "documents") val businessList: List<BusinessDto>)

data class BusinessDto(
    @Json(name = "id") val id: Long,
    @Json(name = "lat") val latitude: String,
    @Json(name = "lon") val longitude: String,
    @Json(name = "tags") val tags: BusinessTagDto,
)

data class BusinessTagDto(
    @Json(name = "name")
    val name: String?,
    @Json(name = "amenity")
    val type: String,
    @Json(name = "operator")
    val ownerName: String?,
    @Json(name = "phone")
    val phoneNumber: String?,
    @Json(name = "website")
    val webPage: String?,
)


fun BusinessContainerDto.asDomainModel(): List<Business> {
    return businessList.map {
        Business(
            id = it.id,
            name = it.tags.name,
            type = it.tags.type,
            ownerName = it.tags.ownerName,
            latitude = it.latitude,
            longitude = it.longitude,
            phoneNumber = it.tags.phoneNumber,
            webPage = it.tags.webPage
        )
    }
}

fun BusinessContainerDto.asDatabaseModel(): List<DatabaseBusiness> {
    return businessList.map {
        DatabaseBusiness(
            id = it.id,
            name = it.tags.name,
            type = it.tags.type,
            ownerName = it.tags.ownerName,
            latitude = it.latitude,
            longitude = it.longitude,
            phoneNumber = it.tags.phoneNumber,
            webPage = it.tags.webPage
        )
    }
}
