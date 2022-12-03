package sk.mobv.androidprojectschool.viewModels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.mobv.androidprojectschool.R
import sk.mobv.androidprojectschool.domain.Business
import sk.mobv.androidprojectschool.repository.BusinessRepository
import java.io.IOException

class BusinessViewModel(
    private val businessRepository: BusinessRepository
) : ViewModel() {

    private var _networkError: MutableLiveData<Boolean?> = MutableLiveData(null)
    val networkError: LiveData<Boolean?>
        get() = _networkError

    private var _isSortAsc: MutableLiveData<Boolean> = MutableLiveData(true)

    val businessList: LiveData<List<Business>> = Transformations.switchMap(_isSortAsc) { sort ->
        if (sort) businessRepository.getBusinessListAsc() else businessRepository.getBusinessListDesc()
    }

    lateinit var business: LiveData<Business>


    fun getBusiness(businessId: Long): LiveData<Business> {
        business = businessRepository.getBusiness(businessId)
        return business
    }

    fun addBusiness(business: Business) {
        viewModelScope.launch {
            businessRepository.addBusiness(business.asDatabaseModel())
        }
    }

    fun editBusiness(business: Business) {
        viewModelScope.launch {
            businessRepository.editBusiness(business.asDatabaseModel())
        }
    }

    fun deleteBusiness(business: Business) {
        viewModelScope.launch {
            businessRepository.deleteBusiness(business.asDatabaseModel())
        }
    }

    fun sortBusinessList(): Boolean {
        _isSortAsc.value = (_isSortAsc.value)?.not()
        return _isSortAsc.value == true
    }

    fun validateEntry(latitude: String, longitude: String): MutableList<Int> {
        val entryErrorResources = mutableListOf<Int>()

        if (latitude.isBlank()) {
            entryErrorResources.add(R.string.form_latitude)
        }

        if (longitude.isBlank()) {
            entryErrorResources.add(R.string.form_longitude)
        }

        return entryErrorResources
    }

    fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                businessRepository.refreshBusinessList()
                this@BusinessViewModel._networkError.value = false
            } catch (networkError: IOException) {
                this@BusinessViewModel._networkError.value = true
            }
        }
    }

}

class BusinessViewModelFactory(private val repository: BusinessRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusinessViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BusinessViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}