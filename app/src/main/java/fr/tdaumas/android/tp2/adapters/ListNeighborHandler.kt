package fr.tdaumas.android.tp2.adapters

import fr.tdaumas.android.tp2.models.Neighbor

interface ListNeighborHandler {
    fun onDeleteNeighbor(neighbor: Neighbor)
    fun onDetailNeighbor(neighbor: Neighbor)
}
