package com.interview.thenewyorktimes.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.interview.thenewyorktimes.ui.home.ScreenSlidePageFragment

/**
 * Created by akshay on 15,November,2020
 * akshay2211@github.io
 */
class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private var headings = arrayOf(
        "home",
        "arts",
        "automobiles",
        "books"/*, "business", "fashion", "food", "health", "home", "insider", "magazine", "movies", "nyregion", "obituaries", "opinion", "politics", "realestate", "science", "sports", "sundayreview", "technology", "theater", "t-magazine", "travel", "upshot", "us", "world"*/
    )

    override fun getCount(): Int = headings.size
    override fun getPageTitle(position: Int): CharSequence = headings[position]
    override fun getItem(position: Int): Fragment =
        ScreenSlidePageFragment.newInstance(headings[position])
}
