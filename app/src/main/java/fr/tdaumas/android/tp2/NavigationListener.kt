package fr.tdaumas.android.tp2

import androidx.fragment.app.Fragment

interface NavigationListener {
    fun showFragment(fragment: Fragment)
    fun updateTitle(title: Int)
}