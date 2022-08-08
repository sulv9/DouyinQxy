package com.qxy.lib.base.base.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

typealias HandleFragment = () -> Fragment

open class SimpleViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    private val mFragmentList = mutableListOf<HandleFragment>()

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position].invoke()
    }

    fun add(fragment: HandleFragment): SimpleViewPagerAdapter {
        mFragmentList.add(fragment)
        return this
    }

    fun add(fragmentList: List<HandleFragment>): SimpleViewPagerAdapter {
        mFragmentList.addAll(fragmentList)
        return this
    }
}