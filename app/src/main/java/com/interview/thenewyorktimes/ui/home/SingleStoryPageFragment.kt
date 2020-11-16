package com.interview.thenewyorktimes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.interview.thenewyorktimes.R
import com.interview.thenewyorktimes.model.Results
import com.interview.thenewyorktimes.ui.adapters.StoriesAdapter
import com.interview.thenewyorktimes.utility.GlideRequests
import com.interview.thenewyorktimes.utility.State
import com.interview.thenewyorktimes.utility.startSinglePageActivity
import kotlinx.android.synthetic.main.loading_error_layout.view.*
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
        data.pagedList.observe(requireActivity(), {
            view.recycler_view.adapter = StoriesAdapter(it, param1 ?: "", glideRequests).apply {
                onStoriesClick = object : StoriesAdapter.OnStoriesClick {
                    override fun bookmarkMethod(results: Results, adapterPosition: Int) {
                        /**
                         * bookmarks are stored local database
                         * both in stories and in bookmarks table
                         * */
                        liveViewModel.bookmark(results) {
                            requireActivity().runOnUiThread {
                                Snackbar.make(
                                    view.main_container,
                                    if (it) {
                                        R.string.bookmarked_done
                                    } else {
                                        R.string.bookmarked_removed
                                    },
                                    Snackbar.LENGTH_LONG
                                ).show()
                                view.recycler_view.scrollToPosition(adapterPosition)
                            }
                        }

                    }

                    override fun openDetailsPage(results: Results) {
                        requireActivity().startSinglePageActivity(result = results)
                    }
                }
            }
        })
        data.networkState.observe(requireActivity(), {
            when (it.State) {
                State.RUNNING -> {
                    view.loading.visibility = View.VISIBLE
                    view.loading_view.visibility = View.VISIBLE
                    view.recycler_view.visibility = View.GONE
                    view.error_layout.visibility = View.GONE
                }
                State.SUCCESS -> {
                    view.loading.visibility = View.GONE
                    view.recycler_view.visibility = View.VISIBLE
                }
                State.FAILED -> {
                    view.loading.visibility = View.VISIBLE
                    view.recycler_view.visibility = View.GONE
                    view.loading_view.visibility = View.GONE
                    view.error_layout.visibility = View.VISIBLE
                    view.error_text.text = it.msg
                }
            }
        })

    }
}
