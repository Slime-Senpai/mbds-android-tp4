package fr.tdaumas.android.tp2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import fr.tdaumas.android.tp2.NavigationListener
import fr.tdaumas.android.tp2.R
import fr.tdaumas.android.tp2.databinding.ActivityMainBinding
import fr.tdaumas.android.tp2.di.DI
import fr.tdaumas.android.tp2.repositories.NeighborRepository
import fr.tdaumas.android.tp2.ui.fragments.ListNeighborsFragment

class MainActivity : AppCompatActivity(), NavigationListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.inject(application)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        fragment = ListNeighborsFragment()

        binding.switchPersist.setOnCheckedChangeListener { _, b ->
            NeighborRepository.getInstance().changeDataSource(b)
            (fragment as? ListNeighborsFragment)?.refreshList()
        }

        showFragment(fragment)
    }

    override fun showFragment(fragment: Fragment) {
        this.fragment = fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }.commit()
    }

    override fun updateTitle(title: Int) {
        binding.toolbar.setTitle(title)
    }
}
