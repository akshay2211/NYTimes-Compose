package io.ak1.nytimes.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.ak1.nytimes.R
import io.ak1.nytimes.data.local.AppDatabase
import io.ak1.nytimes.model.Bookmarks
import io.ak1.nytimes.ui.adapters.BookmarksAdapter
import io.ak1.nytimes.ui.home.deleteBookmark
import io.ak1.nytimes.utility.GlideRequests
import io.ak1.nytimes.utility.startSinglePageActivity
import kotlinx.android.synthetic.main.fragment_bookmark.view.*
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

/**
 * [BookmarkFragment] contains the list of bookmarks
 * locally stored in database
 * */
class BookmarkFragment : Fragment() {
    val db by inject<AppDatabase>()
    private val glideRequests by inject<GlideRequests>()
    val coroutineContext by inject<CoroutineContext>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
        observe(view)
    }

    private fun observe(view: View) {
        db.bookmarksDao().getBookmarks().observe(requireActivity(), {
            (view.recycler_view_bookmarks.adapter as BookmarksAdapter).update(it)
        })
    }

    private fun setup(view: View) {
        view.back.setOnClickListener { requireActivity().onBackPressed() }
        view.recycler_view_bookmarks.adapter = BookmarksAdapter(glideRequests).apply {
            onBookmarkItemClick = object : BookmarksAdapter.OnBookmarkItemClick {
                override fun openBookmark(bookmarks: Bookmarks) {
                    requireActivity().startSinglePageActivity(bookmarks = bookmarks)
                }

                override fun deleteBookmark(bookmarks: Bookmarks) {
                    MaterialAlertDialogBuilder(requireContext()).setTitle(R.string.delete_post_title)
                        .setMessage(R.string.confirm_to_delete)
                        .setPositiveButton(
                            android.R.string.ok
                        ) { _, _ ->
                            db.deleteBookmark(bookmarks.id, coroutineContext)
                        }.setNegativeButton(android.R.string.cancel, null).show()
                }
            }
        }

    }

}


