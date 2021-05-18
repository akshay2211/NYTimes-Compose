package io.ak1.nytimes.utility

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Created by akshay on 16,November,2020
 * akshay2211@github.io
 */
class CustomSwipeRefreshLayout(context: Context?, attrs: AttributeSet?) : SwipeRefreshLayout(
    context!!, attrs
) {
    // The X coordinate of the last touch
    private var mPreDownX = 0f
    private var mPreDownY = 0f
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mPreDownX = ev.x
                mPreDownY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val eventX = ev.x
                val eventY = ev.y
                val xAbs = Math.abs(eventX - mPreDownX)
                val yAbs = Math.abs(eventY - mPreDownY)
                // If the distance moved by the X axis is greater than the distance moved by the Y axis
                // Then don't intercept the touch event and hand it to the following processing
                if (xAbs > yAbs) {
                    return false
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }
}