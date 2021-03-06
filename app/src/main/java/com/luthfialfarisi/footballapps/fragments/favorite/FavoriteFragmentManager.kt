package com.luthfialfarisi.footballapps.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.luthfialfarisi.footballapps.R
import com.luthfialfarisi.footballapps.fragments.match.LastMatchFragment
import kotlinx.android.synthetic.main.fragment_main_favorite.*

class FavoriteFragmentManager : Fragment() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mSectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)

        viewpagerFavorite.adapter = mSectionsPagerAdapter

        viewpagerFavorite.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabsTeam))
        tabsTeam.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewpagerFavorite))
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return FavoriteMatchFragment()
                1 -> return FavoriteTeamFragment()
            }
            return LastMatchFragment()
        }

        override fun getCount(): Int {
            return 2
        }
    }

}