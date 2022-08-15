package com.qxy.module.rank.ui.bottomversion

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.qxy.lib.base.ext.trueHeight
import com.qxy.lib.common.R
import kotlin.math.abs
import kotlin.math.sqrt

class SlideLayoutManager(
    private val context: Context,
    private val onItemSelectedListener: (position: Int) -> Unit,
) : LinearLayoutManager(context) {

    private lateinit var recyclerView: RecyclerView

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        this.recyclerView = view
        LinearSnapHelper().attachToRecyclerView(recyclerView)
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)

        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            // 滑动停止时找到距rv中间最近的Item
            val recyclerCenter = (recyclerView.top + recyclerView.bottom) / 2
            var minDistance = recyclerView.height
            var position = -1
            for (i in 0 until recyclerView.childCount) {
                val child = recyclerView.getChildAt(i)
                val childCenter = (getDecoratedTop(child) + getDecoratedBottom(child)) / 2
                val childDistanceFromCenter = abs(childCenter - recyclerCenter)
                if (childDistanceFromCenter < minDistance) {
                    minDistance = childDistanceFromCenter
                    position = recyclerView.getChildLayoutPosition(child)
                }
            }
            onItemSelectedListener.invoke(position)
        }
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        setupView()
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        setupView()
        return super.scrollVerticallyBy(dy, recycler, state)
    }

    private fun setupView() {
        val midHeight = height / 2.0f
        for (i in 0 until childCount) {
            val child = recyclerView.getChildAt(i)
            val childCenter = (getDecoratedTop(child) + getDecoratedBottom(child)) / 2.0F
            val childDistanceFromCenter = abs(childCenter - midHeight)
            // scale view
            val scale = 1 - sqrt(childDistanceFromCenter / width) * 0.66F
            child.scaleX = scale
            child.scaleY = scale
            // set color
            if (child is MaterialTextView) {
                child.setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (childDistanceFromCenter <= child.trueHeight / 2.0f)
                            R.color.black
                        else
                            R.color.gray
                    )
                )
            }
        }
    }

}