package fr.tdaumas.android.tp2.di

import android.app.Application
import fr.tdaumas.android.tp2.repositories.NeighborRepository

object DI {
    lateinit var repository: NeighborRepository
    fun inject(application: Application) {
        repository = NeighborRepository.getInstance(application)
    }
}