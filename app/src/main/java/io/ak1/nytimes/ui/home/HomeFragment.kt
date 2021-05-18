package io.ak1.nytimes.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.ak1.nytimes.R
import io.ak1.nytimes.data.local.AppDatabase
import io.ak1.nytimes.ui.adapters.ViewPagerAdapter
import io.ak1.nytimes.utility.isNetworkAvailable
import io.ak1.nytimes.utility.startSettingsActivity
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext


/**
 * [HomeFragment] container of view pager
 * contains pull to refresh layer
 * */
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
        initSwipeToRefresh(view)
    }

    private fun initSwipeToRefresh(view: View) {

        view.swipe_refresh.setOnRefreshListener {
            view.swipe_refresh.isRefreshing = true
            if (!requireActivity().isNetworkAvailable()) {
                Snackbar.make(view, R.string.internet_error, Snackbar.LENGTH_LONG).show()
                view.swipe_refresh.isRefreshing = false
            } else {
                db.deleteAll(coroutineContext)
                Handler(Looper.getMainLooper()).postDelayed({
                    setup(view)
                    view.swipe_refresh.isRefreshing = false
                }, 1000L)
            }
        }
    }
}

