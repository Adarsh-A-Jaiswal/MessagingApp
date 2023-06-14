package com.example.messagingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.messagingapp.R
import com.example.messagingapp.common.Constants
import com.example.messagingapp.databinding.FragmentContactInfoBinding
import com.example.messagingapp.modelsClasses.Contact
import com.example.messagingapp.viewModels.SharedViewModel
import com.google.gson.Gson

class ContactInfoFragment : Fragment() {

    private var _binding: FragmentContactInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var contactInfo: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val infoJsonString = it.getString(Constants.CONTACT_JSON_KEY)
            contactInfo = Gson().fromJson(infoJsonString, Contact::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentContactInfoBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.subtitle = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = ""
        setUpBackPressed()
        binding.tvLogo.text = contactInfo.firstName?.first().toString()
        binding.tvName.text =
            getString(R.string.person_full_name, contactInfo.firstName, contactInfo.lastName)
        binding.tvMobile.text = getString(
            R.string.mobile_with_code, contactInfo.countryCode, contactInfo.mobileNumber.toString()
        )

        binding.btnSend.setOnClickListener {
            viewModel.navigateToComposeMessageFragment.postValue(arguments)
        }
    }

    private fun setUpBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().onNavigateUp()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) = ContactInfoFragment().apply {
            arguments = bundle
        }
    }
}