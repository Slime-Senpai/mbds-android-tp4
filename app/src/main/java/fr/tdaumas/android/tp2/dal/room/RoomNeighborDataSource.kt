package fr.tdaumas.android.tp2.dal.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import fr.tdaumas.android.tp2.dal.NeighborDatasource
import fr.tdaumas.android.tp2.dal.room.daos.NeighborDao
import fr.tdaumas.android.tp2.dal.utils.toEntity
import fr.tdaumas.android.tp2.dal.utils.toNeighbor
import fr.tdaumas.android.tp2.models.Neighbor

class RoomNeighborDataSource(application: Application) : NeighborDatasource {
    private val database: NeighborDataBase = NeighborDataBase.getDataBase(application)
    private val dao: NeighborDao = database.neighborDao()

    private val _neighors = MediatorLiveData<List<Neighbor>>()

    init {
        _neighors.addSource(dao.getNeighbors()) { entities ->
            _neighors.value = entities.map { entity ->
                entity.toNeighbor()
            }
        }
    }

    override val neighbours: LiveData<List<Neighbor>>
        get() = _neighors

    override fun deleteNeighbour(neighbor: Neighbor) {
        Thread {
            dao.delete(neighbor.toEntity())
        }.start()
    }

    override fun createNeighbour(neighbor: Neighbor) {
        Thread {
            dao.add(neighbor.toEntity())
        }.start()
    }

    override fun updateFavoriteStatus(neighbor: Neighbor) {
        Thread {
            dao.update(Neighbor(
                neighbor.id,
                neighbor.name,
                neighbor.avatarUrl,
                neighbor.address,
                neighbor.phoneNumber,
                neighbor.aboutMe,
                !neighbor.favorite,
                neighbor.webSite
            ).toEntity())
        }.start()
    }

    override fun updateNeighbour(neighbor: Neighbor) {
        Thread {
            dao.update(neighbor.toEntity())
        }.start()
    }
}