package com.example.messagingapp.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.messagingapp.R
import com.example.messagingapp.common.Constants
import com.example.messagingapp.databinding.ActivityInfoBinding
import com.example.messagingapp.fragments.ComposeMessageFragment
import com.example.messagingapp.fragments.ContactInfoFragment
import com.example.messagingapp.viewModels.SharedViewModel

class InfoActivity : AppCompatActivity() {
    private val viewModel: SharedViewModel by viewModels()
    lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setObservers()

        val bundle = intent.extras?.getBundle("bundle")
        bundle?.let {
            val tabKey = it.getString(Constants.CONTACT_TAB_KEY)
            setFragment(tabKey, it)
        }

    }

    private fun setObservers() {
        viewModel.navigateToComposeMessageFragment.observe(this) { bundle ->
            bundle?.let {
                openComposeMessageFragment(it)
            }
        }
    }

    private fun setFragment(tabKey: String?, bundle: Bundle) {
        tabKey?.let {
            if (tabKey == Constants.CONTACT) {
                //open ContactInfo fragment
                openContactInfoFragment(bundle)
            } else {
                //open Compose message fragment
                openComposeMessageFragment(bundle)
            }
        }
    }

    private fun openContactInfoFragment(bundle: Bundle) {
        this.supportFragmentManager.beginTransaction()
            .add(R.id.nav_host_fragment, ContactInfoFragment.newInstance(bundle))
            .addToBackStack("Back").commit()
    }

    private fun openComposeMessageFragment(bundle: Bundle) {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, ComposeMessageFragment.newInstance(bundle))
            .addToBackStack("Back").commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        if (this.supportFragmentManager.backStackEntryCount <= 1) {
            finish()
        } else {
            this.supportFragmentManager.popBackStack()
        }
        return false
    }
}