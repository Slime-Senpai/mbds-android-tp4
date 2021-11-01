package fr.tdaumas.android.tp2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputLayout
import fr.tdaumas.android.tp2.NavigationListener
import fr.tdaumas.android.tp2.R
import fr.tdaumas.android.tp2.models.Neighbor
import fr.tdaumas.android.tp2.repositories.NeighborRepository

class DetailNeighborFragment(private val neighbor: Neighbor): Fragment() {
    private lateinit var backToNeighbors: Button
    private lateinit var imagePreview: ImageView
    private lateinit var name: TextView
    private lateinit var phone: TextView
    private lateinit var website: TextView
    private lateinit var address: TextView
    private lateinit var about: TextView
    private lateinit var favorite: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.detail_neighbor, container, false)
        backToNeighbors = view.findViewById(R.id.backToNeighborsList)
        imagePreview = view.findViewById(R.id.neighborImage)
        name = view.findViewById(R.id.neighborName)
        phone = view.findViewById(R.id.neighborPhone)
        website = view.findViewById(R.id.neighborWebsite)
        address = view.findViewById(R.id.neighborAddress)
        about = view.findViewById(R.id.neighborAboutMe)
        favorite = view.findViewById(R.id.neighborFavorite)

        backToNeighbors.setOnClickListener {
            (activity as? NavigationListener)?.showFragment(ListNeighborsFragment())
        }

        (activity as? NavigationListener)?.updateTitle(R.string.detailNeighborTitle)

        Glide.with(this)
            .load(neighbor.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_baseline_person_outline_24)
            .error(R.drawable.ic_baseline_person_outline_24)
            .skipMemoryCache(false)
            .into(imagePreview)

        name.text = neighbor.name
        phone.text = neighbor.phoneNumber
        website.text = neighbor.webSite
        address.text = neighbor.address
        about.text = neighbor.aboutMe
        favorite.text = if(neighbor.favorite) getString(R.string.makeFavorite) else getString(R.string.removeFavorite)

        favorite.setOnClickListener {
            this.favoriteNeighbor()
            favorite.text = if(favorite.text == getString(R.string.makeFavorite)) getString(R.string.removeFavorite) else getString(R.string.makeFavorite)
        }

        return view
    }

    private fun favoriteNeighbor() {
        NeighborRepository.getInstance().updateNeighborFavorite(neighbor)
        val toastText: Int = if(favorite.text == getString(R.string.makeFavorite)) R.string.neighborAddedToFavorite else R.string.neighborRemovedFromFavorite
        Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
        // (activity as? NavigationListener)?.showFragment(ListNeighborsFragment())
    }

}
