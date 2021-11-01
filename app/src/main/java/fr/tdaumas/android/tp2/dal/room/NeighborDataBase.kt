package fr.tdaumas.android.tp2.dal.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import fr.tdaumas.android.tp2.dal.memory.inMemoryNeighbors
import fr.tdaumas.android.tp2.dal.room.daos.NeighborDao
import fr.tdaumas.android.tp2.dal.room.entities.NeighborEntity
import fr.tdaumas.android.tp2.dal.utils.toEntity
import java.util.concurrent.Executors

@Database(
    entities = [NeighborEntity::class],
    version = 1
)
abstract class NeighborDataBase : RoomDatabase() {
    abstract fun neighborDao(): NeighborDao

    companion object {
        private var instance: NeighborDataBase? = null
        fun getDataBase(application: Application): NeighborDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    application.applicationContext,
                    NeighborDataBase::class.java,
                    "neighbor_database.db"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        insertFakeData()
                    }
                })
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }

        private fun insertFakeData() {
            Executors.newSingleThreadExecutor().execute {
                inMemoryNeighbors.forEach {
                    instance?.neighborDao()?.add(it.toEntity())
                }
            }
        }
    }
}