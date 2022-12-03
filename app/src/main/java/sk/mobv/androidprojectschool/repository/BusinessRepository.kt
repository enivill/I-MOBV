package sk.mobv.androidprojectschool.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sk.mobv.androidprojectschool.database.BusinessDao
import sk.mobv.androidprojectschool.database.DatabaseBusiness
import sk.mobv.androidprojectschool.database.asDomainModel
import sk.mobv.androidprojectschool.domain.Business
import sk.mobv.androidprojectschool.network.BusinessApi
import sk.mobv.androidprojectschool.network.BusinessContainerDto
import sk.mobv.androidprojectschool.network.asDatabaseModel

class BusinessRepository(private val businessDao: BusinessDao) {
    fun getBusinessListAsc(): LiveData<List<Business>> = Transformations.map(businessDao.getAllBusinessesAsc().asLiveData()) {
        it.asDomainModel()
    }

    fun getBusinessListDesc(): LiveData<List<Business>> = Transformations.map(businessDao.getAllBusinessesDesc().asLiveData()) {
        it.asDomainModel()
    }

    fun getBusiness(businessId: Long): LiveData<Business> {
        return Transformations.map(businessDao.getBusiness(businessId).asLiveData()) {
            it.asDomainModel()
        }
    }

    suspend fun addBusiness(databaseBusiness: DatabaseBusiness) {
        businessDao.insert(databaseBusiness)
    }

    suspend fun editBusiness(databaseBusiness: DatabaseBusiness) {
        businessDao.update(databaseBusiness)
    }

    suspend fun deleteBusiness(databaseBusiness: DatabaseBusiness) {
        businessDao.delete(databaseBusiness)
    }


    suspend fun refreshBusinessList() {
        withContext(Dispatchers.IO) {
            val businessContainer: BusinessContainerDto = BusinessApi.retrofitService.getBusinesses()
            businessDao.insertAll(businessContainer.asDatabaseModel())
        }
    }
}