package sk.mobv.androidprojectschool

import android.app.Application
import sk.mobv.androidprojectschool.database.AppDatabase
import sk.mobv.androidprojectschool.repository.BusinessRepository

class BusinessApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { BusinessRepository(database.businessDao()) }
}