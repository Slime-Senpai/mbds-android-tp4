package fr.tdaumas.android.tp2.dal.memory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.tdaumas.android.tp2.dal.NeighborDatasource
import fr.tdaumas.android.tp2.models.Neighbor
import kotlin.math.floor

class InMemoryNeighborDataSource : NeighborDatasource {
    private val observable: LiveData<List<Neighbor>> = MutableLiveData(inMemoryNeighbors)

    override val neighbours: LiveData<List<Neighbor>>
        get() = observable

    override fun deleteNeighbour(neighbor: Neighbor) {
        inMemoryNeighbors.remove(neighbor)
    }

    override fun createNeighbour(neighbor: Neighbor) {
        inMemoryNeighbors.add(Neighbor(
            getNewId(),
            neighbor.name,
            neighbor.avatarUrl,
            neighbor.address,
            neighbor.phoneNumber,
            neighbor.aboutMe,
            neighbor.favorite,
            neighbor.webSite
        ))
    }

    override fun updateFavoriteStatus(neighbor: Neighbor) {
        if (inMemoryNeighbors.indexOf(neighbor) != -1) {

            inMemoryNeighbors[inMemoryNeighbors.indexOf(neighbor)] = Neighbor(
                neighbor.id,
                neighbor.name,
                neighbor.avatarUrl,
                neighbor.address,
                neighbor.phoneNumber,
                neighbor.aboutMe,
                !neighbor.favorite,
                neighbor.webSite)
        }
    }

    override fun updateNeighbour(neighbor: Neighbor) {
        if (inMemoryNeighbors.indexOf(neighbor) != -1) {
            inMemoryNeighbors[inMemoryNeighbors.indexOf(neighbor)] = neighbor
        }
    }

    private fun getNewId(): Long {
        return floor(Math.random() * 100000).toLong()
    }
}
