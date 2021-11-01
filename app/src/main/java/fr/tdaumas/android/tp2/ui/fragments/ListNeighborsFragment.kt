package fr.tdaumas.android.tp2.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.tdaumas.android.tp2.NavigationListener
import fr.tdaumas.android.tp2.R
import fr.tdaumas.android.tp2.adapters.ListNeighborHandler
import fr.tdaumas.android.tp2.adapters.ListNeighborsAdapter
import fr.tdaumas.android.tp2.repositories.NeighborRepository
import fr.tdaumas.android.tp2.models.Neighbor
import fr.tdaumas.android.tp2.viewmodels.NeighborViewModel

class ListNeighborsFragment : Fragment(), ListNeighborHandler {
    private lateinit var recyclerView: RecyclerView
    private lateinit var addNeighbor: FloatingActionButton
    private lateinit var viewModel: NeighborViewModel

    /**
     * Fonction permettant de définir une vue à attacher à un fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list_neighbors_fragment, container, false)
        recyclerView = view.findViewById(R.id.neighbors_list)
        addNeighbor = view.findViewById(R.id.addNeighborButton)
        viewModel = ViewModelProvider(this).get(NeighborViewModel::class.java)
        
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        (activity as? NavigationListener)?.updateTitle(R.string.listNeighborTitle)

        addNeighbor.setOnClickListener {
            (activity as? NavigationListener)?.showFragment(AddNeighborFragment())
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setData()
    }

    override fun onDeleteNeighbor(neighbor: Neighbor) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog
            .setTitle(R.string.confirmDeleteTitle)
            .setMessage(R.string.confirmDeleteMessage)
            .setPositiveButton(R.string.yes) { _, _ ->
                NeighborRepository.getInstance().deleteNeighbor(neighbor)
                setData()
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.cancel()
            }
        val createdAlertDialog = alertDialog.create()
        createdAlertDialog.show()
    }

    override fun onDetailNeighbor(neighbor: Neighbor) {
        (activity as? NavigationListener)?.showFragment(DetailNeighborFragment(neighbor))
    }

    private fun setData() {
        viewModel.neighbors.observe(viewLifecycleOwner) {
            val adapter = ListNeighborsAdapter(it, this)
            recyclerView.adapter = adapter
        }
    }

    fun refreshList() {
        setData()
    }
}
