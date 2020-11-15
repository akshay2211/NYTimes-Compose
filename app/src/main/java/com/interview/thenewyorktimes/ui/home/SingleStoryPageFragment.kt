package com.interview.thenewyorktimes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.interview.thenewyorktimes.R
import com.interview.thenewyorktimes.model.Results
import com.interview.thenewyorktimes.ui.adapters.StoriesAdapter
import com.interview.thenewyorktimes.utility.GlideRequests
import com.interview.thenewyorktimes.utility.startSinglePageActivity
import kotlinx.android.synthetic.main.stories_page.view.*
import org.koin.android.ext.android.inject

/**
 * Created by akshay on 15,November,2020
 * akshay2211@github.io
 */
private const val ARG_PARAM1 = "param1"

class ScreenSlidePageFragment : Fragment() {
    val glideRequests by inject<GlideRequests>()
    private val liveViewModel by inject<StoriesViewModel>()
    private var param1: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    companion object {
        fun newInstance(param1: String) =
            ScreenSlidePageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.stories_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data = liveViewModel.getStories(param1 ?: "")
        data.pagedList.observe(requireActivity(), Observer {
            view.recycler_view.adapter = StoriesAdapter(it, param1 ?: "", glideRequests).apply {
                onStoriesClick = object : StoriesAdapter.OnStoriesClick {
                    override fun bookmarkMethod(results: Results) {
                        liveViewModel.bookmark(results)
                        Snackbar.make(
                            view.main_container,
                            R.string.bookmarked_done,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                    override fun openDetailsPage(results: Results) {
                        requireActivity().startSinglePageActivity(result = results)
                    }
                }
            }
        })
        data.networkState.observe(requireActivity(), Observer {

        })

    }
}
