package com.interview.thenewyorktimes.ui.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.interview.thenewyorktimes.R
import com.interview.thenewyorktimes.model.Bookmarks
import com.interview.thenewyorktimes.utility.GlideRequests
import com.interview.thenewyorktimes.utility.ScreenDimensions
import kotlinx.android.synthetic.main.bookmark_row.view.*

class BookmarksAdapter(var glideRequests: GlideRequests) :
    RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {
    val list = ArrayList<Bookmarks>()

    interface OnBookmarkItemClick {
        fun openBookmark(bookmarks: Bookmarks)
        fun deleteBookmark(bookmarks: Bookmarks)
    }

    lateinit var onBookmarkItemClick: OnBookmarkItemClick

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener,
        View.OnLongClickListener {
        init {
            view.card_bookmark.setOnClickListener(this)
            view.card_bookmark.setOnLongClickListener(this)
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        override fun onClick(v: View?) {
            var bookmarks = list[adapterPosition]
            onBookmarkItemClick.openBookmark(bookmarks)

        }

        /**
         * Called when a view has been clicked and held.
         *
         * @param v The view that was clicked and held.
         *
         * @return true if the callback consumed the long click, false otherwise.
         */
        override fun onLongClick(v: View?): Boolean {
            var bookmarks = list[adapterPosition]
            onBookmarkItemClick.deleteBookmark(bookmarks)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bookmark_row, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position].let {
            holder.itemView.stories_iv.visibility = if (it.url.isNullOrEmpty()) {
                holder.itemView.card_bookmark.setCardBackgroundColor(Color.DKGRAY)
                holder.itemView.title.setLines(3)
                holder.itemView.title.gravity = Gravity.CENTER
                holder.itemView.gradient_iv.visibility = View.GONE
                View.GONE
            } else {
                holder.itemView.card_bookmark.setCardBackgroundColor(0)
                holder.itemView.title.setLines(1)
                holder.itemView.title.gravity = Gravity.BOTTOM
                holder.itemView.gradient_iv.visibility = View.VISIBLE
                View.VISIBLE
            }
            holder.itemView.title.text = it.title ?: ""
            glideRequests.load(it.url ?: "")
                .placeholder(ColorDrawable(Color.GRAY))
                .error(ColorDrawable(Color.GRAY))
                .thumbnail(
                    glideRequests.load(it.url_thumb)
                        .override((ScreenDimensions.WidthPX / 2), ScreenDimensions.WidthPX / 2)
                        .transform(CenterCrop())
                )
                .transform(CenterCrop())
                .signature(ObjectKey(it.id))
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemView.stories_iv)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update(list: List<Bookmarks>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

}
