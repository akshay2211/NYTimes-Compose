package com.interview.thenewyorktimes.ui.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.interview.thenewyorktimes.R
import com.interview.thenewyorktimes.model.Results
import com.interview.thenewyorktimes.utility.GlideRequests
import com.interview.thenewyorktimes.utility.ScreenDimensions
import com.interview.thenewyorktimes.utility.getDesiredHeight
import com.interview.thenewyorktimes.utility.timeAgo
import kotlinx.android.synthetic.main.stories_row.view.*

/**
 * Created by akshay on 15,November,2020
 * akshay2211@github.io
 */
class StoriesAdapter(var list: List<Results>, var name: String, var glideRequests: GlideRequests) :
    RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {
    interface OnStoriesClick {
        fun bookmarkMethod(results: Results)
        fun openDetailsPage(results: Results)
    }

    lateinit var onStoriesClick: OnStoriesClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesAdapter.ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.stories_row, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position].let {
            var customHeight = 0


            holder.itemView.visibility = if (it.url.isNullOrEmpty()) {
                View.GONE
            } else {
                Log.e("check ----X-- ", "$customHeight  ${it.title} ${it.height / it.width}")
                customHeight = (it.height).getDesiredHeight(actualWidth = it.width)
                holder.itemView.stories_iv.layoutParams.apply {
                    height = customHeight
                }
                holder.itemView.stories_iv.requestLayout()
                View.VISIBLE
            }
            Log.e("check ------- ", "$customHeight  ${it.title}")
            holder.itemView.title.text = it.title ?: ""
            holder.itemView.time.text = it.published_date.timeAgo()
            glideRequests.load(it.url ?: "")
                .placeholder(ColorDrawable(Color.GRAY))
                .error(ColorDrawable(Color.GRAY))
                .thumbnail(
                    glideRequests.load(it.url_thumb)
                        .override((ScreenDimensions.WidthPX), customHeight)
                        .transform(CenterCrop())
                )
                .transform(CenterCrop())
                .signature(ObjectKey(it.id))
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemView.stories_iv)
        }

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return list.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            view.bookmark_icon.setOnClickListener(this)
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            var results = list[adapterPosition]
            if (v?.id == R.id.bookmark_icon) {
                onStoriesClick.bookmarkMethod(results)
                return
            }
            onStoriesClick.openDetailsPage(results)

        }

    }
}