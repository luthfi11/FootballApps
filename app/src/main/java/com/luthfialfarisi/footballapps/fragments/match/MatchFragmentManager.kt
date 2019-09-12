package com.luthfialfarisi.footballapps.fragments.match

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.luthfialfarisi.footballapps.R
import com.luthfialfarisi.footballapps.activities.MatchSearchActivity
import kotlinx.android.synthetic.main.fragment_main_match.*
import org.jetbrains.anko.support.v4.startActivity

class MatchFragmentManager : Fragment() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        mSectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)

        viewpagerMatch.adapter = mSectionsPagerAdapter

        viewpagerMatch.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewpagerMatch))
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return LastMatchFragment()
                1 -> return NextMatchFragment()
            }
            return LastMatchFragment()
        }

        override fun getCount(): Int {
            return 2
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.search_menu_match, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.matchSearch -> {
                startActivity<MatchSearchActivity>()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}