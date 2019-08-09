package com.cato.kotlinprueba.core

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by katherine on 29/03/17.
 */

abstract class RecyclerViewScrollListener : RecyclerView.OnScrollListener() {
    private var mScrollThreshold = 40
    private var scrolledDistance = 0
    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private val visibleThreshold =
        2 // The minimum amount of items to have below your current scroll position before loading more.
    internal var firstVisibleItem: Int = 0
    internal var visibleItemCount: Int = 0
    internal var totalItemCount: Int = 0
    private var infiniteScrollingEnabled = true
    private var controlsVisible = true

    // a1. When the data is refreshed, we need to change previousTotal to a0.
    // a2. When we switch fragments and it loads itself from some place, for some
    // reason gridLayoutManager returns stale data and hence re-assigning it every time.    @Override
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val manager = recyclerView.getLayoutManager()
        visibleItemCount = recyclerView.getChildCount()
        if (manager is GridLayoutManager) {
            val gridLayoutManager = manager as GridLayoutManager
            firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition()
            totalItemCount = gridLayoutManager.getItemCount()
        } else if (manager is LinearLayoutManager) {
            val linearLayoutManager = manager as LinearLayoutManager
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
            totalItemCount = linearLayoutManager.getItemCount()
        }
        if (infiniteScrollingEnabled) {
            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
                // End has been reached
                // do something
                onLoadMore()
                loading = true
            }
        }
        if (firstVisibleItem == 0) {
            if (!controlsVisible) {
                onScrollUp()
                controlsVisible = true
            }
            return
        }
        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
            onScrollDown()
            controlsVisible = false
            scrolledDistance = 0
        } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
            onScrollUp()
            controlsVisible = true
            scrolledDistance = 0
        }
        if (controlsVisible && dy > 0 || !controlsVisible && dy < 0) {
            scrolledDistance += dy
        }
    }

    abstract fun onScrollUp()
    abstract fun onScrollDown()
    abstract fun onLoadMore()
    fun setScrollThreshold(scrollThreshold: Int) {
        mScrollThreshold = scrollThreshold
    }

    fun stopInfiniteScrolling() {
        infiniteScrollingEnabled = false
    }

    fun onDataCleared() {
        previousTotal = 0
    }

    companion object {

        private val TAG = "RecylcerScrollListener"
        private val HIDE_THRESHOLD = 20
    }
}//        Log.i(TAG, "construct");
// So TWO issues here.
