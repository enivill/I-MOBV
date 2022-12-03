package sk.mobv.androidprojectschool.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface BusinessDao {

    @Query("SELECT * from businesses WHERE id = :id")
    fun getBusiness(id: Long): Flow<DatabaseBusiness>

    @Query("SELECT * from businesses ORDER BY name COLLATE NOCASE ASC")
    fun getAllBusinessesAsc(): Flow<List<DatabaseBusiness>>

    @Query("SELECT * from businesses ORDER BY name COLLATE NOCASE DESC")
    fun getAllBusinessesDesc(): Flow<List<DatabaseBusiness>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(business: DatabaseBusiness)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(businesses: List<DatabaseBusiness>)

    @Update
    suspend fun update(business: DatabaseBusiness)

    @Delete
    suspend fun delete(business: DatabaseBusiness)
}