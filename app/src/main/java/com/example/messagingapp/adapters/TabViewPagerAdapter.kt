package com.example.messagingapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.messagingapp.fragments.ContactsFragment
import com.example.messagingapp.fragments.MessagesFragment

class TabViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    // it returns total number of tabs in ViewPager
    override fun getItemCount(): Int = 2

    //it create fragments according to tab position
    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> {
                ContactsFragment()
            }

            1 -> {
                MessagesFragment()
            }

            else -> {
                ContactsFragment()
            }
        }
}