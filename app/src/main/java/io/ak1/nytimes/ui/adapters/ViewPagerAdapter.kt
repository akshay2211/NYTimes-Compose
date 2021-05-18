package io.ak1.nytimes.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import io.ak1.nytimes.ui.home.ScreenSlidePageFragment

/**
 * Created by akshay on 15,November,2020
 * akshay2211@github.io
 */
class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    /**
     *  adding only few to keep network calls on check
     *  can be done dynamically via storing list in preference and limiting the selection count
     */
    private var headings = arrayOf(
        "Movies",
        "Food",
        "Automobiles",
        "Books",
        "Travel",
        "Business"//, "food", "health", "home", "insider", "magazine", "movies", "nyregion", "obituaries", "opinion", "politics", "realestate", "science", "sports", "sundayreview", "technology", "theater", "t-magazine", "travel", "upshot", "us", "world"
    )

    override fun getCount(): Int = headings.size
    override fun getPageTitle(position: Int): CharSequence = headings[position]
    override fun getItem(position: Int): Fragment =
        ScreenSlidePageFragment.newInstance(headings[position])
}
