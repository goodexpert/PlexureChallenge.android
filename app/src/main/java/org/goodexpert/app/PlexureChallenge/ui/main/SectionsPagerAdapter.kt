package org.goodexpert.app.PlexureChallenge.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.goodexpert.app.PlexureChallenge.R
import org.goodexpert.app.PlexureChallenge.ui.favorite.FavoriteFragment
import org.goodexpert.app.PlexureChallenge.ui.store.StoresFragment

private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment
        when (position) {
            0 -> {
                fragment = StoresFragment.newInstance()
            }
            else -> {
                fragment = FavoriteFragment.newInstance()
            }
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}