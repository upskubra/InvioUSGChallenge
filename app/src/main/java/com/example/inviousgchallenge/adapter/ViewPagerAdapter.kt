package com.example.inviousgchallenge.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.inviousgchallenge.ui.ApiImagesFragment
import com.example.inviousgchallenge.ui.FeedFragment
import com.example.inviousgchallenge.ui.TrashFragment


class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val NUM_TABS = 3
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return FeedFragment()
            1 -> return TrashFragment()
            2 -> return ApiImagesFragment()
        }
        return FeedFragment()
    }
}