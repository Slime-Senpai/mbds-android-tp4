package fr.tdaumas.android.tp2.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import fr.tdaumas.android.tp2.di.DI
import fr.tdaumas.android.tp2.models.Neighbor
import fr.tdaumas.android.tp2.repositories.NeighborRepository

class NeighborViewModel : ViewModel() {
    private val repository: NeighborRepository = DI.repository

    val neighbors: LiveData<List<Neighbor>>
        get() = repository.getNeighbours()
}