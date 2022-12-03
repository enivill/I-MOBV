package sk.mobv.androidprojectschool.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(DatabaseBusiness::class), version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun businessDao(): BusinessDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .build()
//                    .fallbackToDestructiveMigration()
                    //.createFromAsset("database/businesses.db")

                INSTANCE = instance
                return instance
            }
        }
    }
}