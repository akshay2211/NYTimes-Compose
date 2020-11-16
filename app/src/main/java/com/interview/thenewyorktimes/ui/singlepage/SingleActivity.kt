package com.interview.thenewyorktimes.ui.singlepage

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.interview.thenewyorktimes.R
import com.interview.thenewyorktimes.model.Bookmarks
import com.interview.thenewyorktimes.utility.*
import kotlinx.android.synthetic.main.activity_single.*
import org.koin.android.ext.android.inject


class SingleActivity : AppCompatActivity() {
    private val glideRequests by inject<GlideRequests>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setUpStatusNavigationBarColors(
            isDarkThemeOn(),
            ContextCompat.getColor(this, R.color.background)
        )
        setContentView(R.layout.activity_single)
        setup()
    }

    private fun setup() {
        var bookmarks = intent.getSerializableExtra("bookmark") as Bookmarks?
        bookmarks?.let {
            var customHeight = 0

            story_card.visibility = if (it.url.isNullOrEmpty()) {
                View.GONE
            } else {
                customHeight = (it.height).getDesiredHeight(actualWidth = it.width)
                stories_iv.layoutParams.apply {
                    height = customHeight
                }
                stories_iv.requestLayout()
                View.VISIBLE
            }
            title_page.text = it.title ?: ""
            abstract_text.text = it.abstract_text ?: ""
            time_page.text = it.published_date.timeAgo()
            glideRequests.load(it.url_large ?: "")
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
                .into(stories_iv)
            val url = it.url

            externalLink.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                })
            }
            back.setOnClickListener { finish() }
        }
    }
}