package com.interview.thenewyorktimes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.interview.thenewyorktimes.R
import com.interview.thenewyorktimes.ui.adapters.ScreenSlidePagerAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun setup(view: View) {
        view.viewPager.adapter = ScreenSlidePagerAdapter(childFragmentManager)
        view.tabLayout.setupWithViewPager(view.viewPager)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
    }
}

