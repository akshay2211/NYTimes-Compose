package com.interview.thenewyorktimes.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.interview.thenewyorktimes.R
import com.interview.thenewyorktimes.data.local.AppDatabase
import com.interview.thenewyorktimes.ui.adapters.ViewPagerAdapter
import com.interview.thenewyorktimes.utility.startSettingsActivity
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext


class HomeFragment : Fragment() {
    private val db by inject<AppDatabase>()
    private val coroutineContext by inject<CoroutineContext>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun setup(view: View) {
        view.viewPager.adapter = ViewPagerAdapter(childFragmentManager)
        view.tabLayout.setupWithViewPager(view.viewPager)
        view.options.setOnClickListener { requireActivity().startSettingsActivity() }
        view.viewPager.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_MOVE -> view.swipe_refresh.isEnabled = false
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> view.swipe_refresh.isEnabled =
                    true
            }
            false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
        initSwipeToRefresh(view)
    }

    private fun initSwipeToRefresh(view: View) {

        view.swipe_refresh.setOnRefreshListener {
            view.swipe_refresh.isRefreshing = true
            db.deleteAll(coroutineContext)
            Handler(Looper.getMainLooper()).postDelayed({
                setup(view)
                view.swipe_refresh.isRefreshing = false
            }, 1000L)
        }
    }
}

