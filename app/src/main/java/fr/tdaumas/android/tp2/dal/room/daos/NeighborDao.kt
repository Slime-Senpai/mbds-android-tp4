package fr.tdaumas.android.tp2.dal.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.tdaumas.android.tp2.dal.room.entities.NeighborEntity

@Dao
interface NeighborDao {
    @Query("SELECT * from neighbors")
    fun getNeighbors(): LiveData<List<NeighborEntity>>

    @Insert
    fun add(neighborEntity: NeighborEntity)

    @Delete
    fun delete(neighbor: NeighborEntity)

    @Update
    fun update(toEntity: NeighborEntity)
}