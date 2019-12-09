package com.dwirandyh.movieapp.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FavoritePagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabFragments = ArrayList<Fragment>()
    private var tabTitles = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return tabFragments[position]
    }

    override fun getCount(): Int {
        return tabFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        tabFragments.add(fragment)
        tabTitles.add(title)
    }
}