package sk.mobv.androidprojectschool.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import sk.mobv.androidprojectschool.domain.Business

@Entity(tableName = "businesses")
data class DatabaseBusiness(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "owner_name")
    val ownerName: String?,
    @ColumnInfo(name = "latitude")
    val latitude: String,
    @ColumnInfo(name = "longitude")
    val longitude: String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String?,
    @ColumnInfo(name = "web")
    val webPage: String?,
)


fun DatabaseBusiness.asDomainModel(): Business {
    return Business(
        id = id,
        name = name,
        type = type,
        ownerName = ownerName,
        latitude = latitude,
        longitude = longitude,
        phoneNumber = phoneNumber,
        webPage = webPage
    )
}

fun List<DatabaseBusiness>.asDomainModel(): List<Business> {
    return map {
        it.asDomainModel()
    }
}