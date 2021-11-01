package fr.tdaumas.android.tp2.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import fr.tdaumas.android.tp2.NavigationListener
import fr.tdaumas.android.tp2.R
import fr.tdaumas.android.tp2.databinding.AddNeighborBinding
import fr.tdaumas.android.tp2.repositories.NeighborRepository
import fr.tdaumas.android.tp2.models.Neighbor
import kotlin.math.floor

class AddNeighborFragment: Fragment() {
    private lateinit var backToNeighbors: Button
    private lateinit var imagePreview: ImageView
    private lateinit var imageURI: TextInputLayout
    private lateinit var name: TextInputLayout
    private lateinit var phone: TextInputLayout
    private lateinit var website: TextInputLayout
    private lateinit var address: TextInputLayout
    private lateinit var about: TextInputLayout
    private lateinit var save: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_neighbor, container, false)
        backToNeighbors = view.findViewById(R.id.backToNeighborsList)
        imagePreview = view.findViewById(R.id.newNeighborImage)
        imageURI = view.findViewById(R.id.newNeighborImageURI)
        name = view.findViewById(R.id.newNeighborName)
        phone = view.findViewById(R.id.newNeighborPhone)
        website = view.findViewById(R.id.newNeighborWebsite)
        address = view.findViewById(R.id.newNeighborAddress)
        about = view.findViewById(R.id.newNeighborAboutMe)
        save = view.findViewById(R.id.newNeighborSave)

        save.isClickable = false
        save.isEnabled = false

        this.verifyInputs()

        backToNeighbors.setOnClickListener {
            (activity as? NavigationListener)?.showFragment(ListNeighborsFragment())
        }

        (activity as? NavigationListener)?.updateTitle(R.string.newNeighborTitle)

        save.setOnClickListener {
            val newNeighbor = Neighbor(
                id = 0,
                name = name.editText?.text.toString(),
                avatarUrl = imageURI.editText?.text.toString(),
                address = address.editText?.text.toString(),
                phoneNumber = phone.editText?.text.toString(),
                aboutMe = about.editText?.text.toString(),
                favorite = false,
                webSite = website.editText?.text.toString()
            )
            
            this.addNeighbor(newNeighbor)
        }

        return view
    }

    private fun addNeighbor(newNeighbor: Neighbor) {
        NeighborRepository.getInstance().addNeighbor(newNeighbor)
        Toast.makeText(context, R.string.newNeighborCreated, Toast.LENGTH_LONG).show()
        (activity as? NavigationListener)?.showFragment(ListNeighborsFragment())
    }

    private fun verifyInputs() {
        this.verifyImageURI()
        this.verifyName()
        this.verifyPhone()
        this.verifyWebsite()
        this.verifyAddress()
        this.verifyAbout()
    }

    private fun verifyImageURI() {
        imageURI.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    imageURI.isErrorEnabled = true
                    imageURI.error = getString(R.string.errorIsRequired, getString(R.string.image)) // "Image is required"
                } else if (!Patterns.WEB_URL.matcher(p0).matches()) {
                    imageURI.isErrorEnabled = true
                    imageURI.error = getString(R.string.errorValidURI, getString(R.string.image)) // "Image needs to be a valid URI"
                } else {
                    imageURI.isErrorEnabled = false
                }

                setSaveEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {
                // Nothing
            }

        })
    }

    private fun verifyName() {
        name.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    name.isErrorEnabled = true
                    name.error = getString(R.string.errorIsRequired, getString(R.string.name)) // "Name is required"
                } else if (p0.length < 4 || p0.length > 20) {
                    name.isErrorEnabled = true
                    name.error = getString(R.string.errorMustBeInBetween, getString(R.string.name), 4, 20)
                } else {
                    name.isErrorEnabled = false
                }

                setSaveEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {
                // Nothing
            }

        })
    }

    private fun verifyPhone() {
        phone.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    phone.isErrorEnabled = true
                    phone.error = getString(R.string.errorIsRequired, getString(R.string.phone))
                } else if (!Patterns.PHONE.matcher(p0).matches()) {
                    phone.isErrorEnabled = true
                    phone.error = getString(R.string.errorValidPhoneNumber, getString(R.string.phone))
                } else {
                    phone.isErrorEnabled = false
                }

                setSaveEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {
                // Nothing
            }

        })
    }

    private fun verifyWebsite() {
        website.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    website.isErrorEnabled = true
                    website.error = getString(R.string.errorIsRequired, getString(R.string.website))// "Website is required"
                } else if (!Patterns.WEB_URL.matcher(p0).matches()) {
                    website.isErrorEnabled = true
                    website.error = getString(R.string.errorValidURI, getString(R.string.website))
                } else {
                    website.isErrorEnabled = false
                }

                setSaveEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {
                // Nothing
            }

        })
    }

    private fun verifyAddress() {
        address.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    address.isErrorEnabled = true
                    address.error = getString(R.string.errorIsRequired, getString(R.string.address))
                } else if (p0.length < 5 || p0.length > 30) {
                    address.isErrorEnabled = true
                    address.error = getString(R.string.errorMustBeInBetween, getString(R.string.address), 5, 30)
                } else {
                    address.isErrorEnabled = false
                }

                setSaveEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {
                // Nothing
            }

        })
    }

    private fun verifyAbout() {
        about.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    about.isErrorEnabled = true
                    about.error = getString(R.string.errorIsRequired, getString(R.string.about))
                } else if (p0.length < 5 || p0.length > 30) {
                    about.isErrorEnabled = true
                    about.error = getString(R.string.errorMustBeInBetween, getString(R.string.about), 5, 30)
                } else {
                    about.isErrorEnabled = false
                }

                setSaveEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {
                // Nothing
            }

        })
    }

    private fun setSaveEnabled() {
        save.setEnabled(!imageURI.isErrorEnabled && !name.isErrorEnabled && !phone.isErrorEnabled &&
                !website.isErrorEnabled && !address.isErrorEnabled && !about.isErrorEnabled)
    }

}