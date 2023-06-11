package com.example.messagingapp.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.messagingapp.R
import com.example.messagingapp.common.Constants
import com.example.messagingapp.databinding.ActivityInfoBinding
import com.example.messagingapp.fragments.ComposeMessageFragment
import com.example.messagingapp.fragments.ContactInfoFragment
import com.example.messagingapp.modelsClasses.Contact
import com.example.messagingapp.viewModels.SharedViewModel
import com.google.gson.Gson

class InfoActivity : AppCompatActivity() {
    private val viewModel: SharedViewModel by viewModels()
    lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setFragment()

        setObservers()

    }

    private fun setObservers() {
        viewModel.navigateToComposeMessageFragment.observe(this) { bundle ->
            bundle?.let {
                openComposeMessageFragment(it)
            }
        }
    }

    private fun setFragment() {
        val extraBundle = intent.extras?.getBundle("bundle")
        extraBundle?.let { bundle ->
            val tabKey = bundle.getString(Constants.CONTACT_TAB_KEY)
            tabKey?.let {
                if (tabKey == Constants.CONTACT) {
                    //open ContactInfo fragment
                    openContactInfoFragment(bundle)
                } else {
                    //open Compose message fragment
                    setToolBarTitle(bundle)
                    openComposeMessageFragment(bundle)
                }
            }
        }
    }

    private fun setToolBarTitle(bundle: Bundle) {
        val infoJsonString = bundle.getString(Constants.CONTACT_JSON_KEY)
        val contactInfo = Gson().fromJson(infoJsonString, Contact::class.java)
        binding.toolbar.title = getString(
            R.string.person_full_name,
            contactInfo.firstName,
            contactInfo.lastName
        )
    }

    private fun openContactInfoFragment(bundle: Bundle) {
        this.supportFragmentManager.beginTransaction()
            .add(R.id.nav_host_fragment, ContactInfoFragment.newInstance(bundle))
            .addToBackStack("Back").commit()
    }

    private fun openComposeMessageFragment(bundle: Bundle) {
        setToolBarTitle(bundle)
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