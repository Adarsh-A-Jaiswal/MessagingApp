package com.example.messagingapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.messagingapp.R
import com.example.messagingapp.adapters.ContactsViewPagerAdapter
import com.example.messagingapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // adding toolbar
        setSupportActionBar(binding.toolbar)
        //setting view pager adapter for tabs
        setViewPagerAdapter()
        //applying name of tabs
        setTabLayoutMediator()
    }

    private fun setTabLayoutMediator() {
        TabLayoutMediator(binding.contactTabLayout, binding.contactViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> {
                    this.getString(R.string.first_tab_title)
                }

                1 -> {
                    this.getString(R.string.second_tab_title)
                }

                else -> {
                    this.getString(R.string.first_tab_title)
                }
            }
        }.attach()
    }

    private fun setViewPagerAdapter() {
        binding.contactViewPager.adapter =
            ContactsViewPagerAdapter(
                fragmentManager = supportFragmentManager,
                lifecycle = lifecycle
            )
    }
}