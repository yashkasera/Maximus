package com.craft404.maximus.ui.home

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomePagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = 3
    override fun createFragment(position: Int) = when (position) {
        0 -> ViewMessageFragment()
        1 -> ViewMessageFragment()
        else -> ViewMessageFragment()
    }
}