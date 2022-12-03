package sk.mobv.androidprojectschool.domain

import sk.mobv.androidprojectschool.database.DatabaseBusiness

data class Business(
    val id: Long = 0,
    var name: String?,
    var type: String,
    var ownerName: String?,
    val latitude: String,
    val longitude: String,
    var phoneNumber: String?,
    var webPage: String?,
){
    fun asDatabaseModel(): DatabaseBusiness {
        return DatabaseBusiness(
            id = id,
            name = if (name.isNullOrEmpty()) null else name,
            type = type,
            ownerName = if (ownerName.isNullOrEmpty()) null else ownerName,
            latitude = latitude,
            longitude = longitude,
            phoneNumber = if (phoneNumber.isNullOrEmpty()) null else phoneNumber,
            webPage = if (webPage.isNullOrEmpty()) null else webPage
        )
    }
}