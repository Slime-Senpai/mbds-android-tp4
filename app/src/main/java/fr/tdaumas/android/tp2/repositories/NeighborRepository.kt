package fr.tdaumas.android.tp2.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import fr.tdaumas.android.tp2.dal.memory.InMemoryNeighborDataSource
import fr.tdaumas.android.tp2.dal.NeighborDatasource
import fr.tdaumas.android.tp2.dal.room.RoomNeighborDataSource
import fr.tdaumas.android.tp2.models.Neighbor

class NeighborRepository private constructor(application: Application) {
    private var dataSource: NeighborDatasource
    private val memoryDataSource: InMemoryNeighborDataSource
    private val roomDataSource: RoomNeighborDataSource

    init {
        memoryDataSource = InMemoryNeighborDataSource()
        roomDataSource = RoomNeighborDataSource(application)
        dataSource = memoryDataSource
    }

    fun getNeighbours(): LiveData<List<Neighbor>> = dataSource.neighbours

    fun deleteNeighbor(neighbor: Neighbor) = dataSource.deleteNeighbour(neighbor)

    fun addNeighbor(neighbor: Neighbor) = dataSource.createNeighbour(neighbor)

    fun changeDataSource (switchState: Boolean) {
        dataSource = if(switchState) roomDataSource else memoryDataSource
    }

    fun updateNeighborFavorite(neighbor: Neighbor) = dataSource.updateFavoriteStatus(neighbor)

    companion object {
        private var instance: NeighborRepository? = null
        fun getInstance(application: Application): NeighborRepository {
            if (instance == null) {
                instance = NeighborRepository(application)
            }
            return instance!!
        }
        fun getInstance(): NeighborRepository {
            if (instance == null) {
                throw Error()
            }
            return instance!!
        }
    }
}
