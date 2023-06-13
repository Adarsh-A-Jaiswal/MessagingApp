package com.example.messagingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.messagingapp.R
import com.example.messagingapp.common.Constants
import com.example.messagingapp.databinding.FragmentComposeMessageBinding
import com.example.messagingapp.modelsClasses.Contact
import com.example.messagingapp.modelsClasses.Message
import com.example.messagingapp.viewModels.SharedViewModel
import com.google.gson.Gson


class ComposeMessageFragment : Fragment() {

    private var _binding: FragmentComposeMessageBinding? = null
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        _binding = FragmentComposeMessageBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSendMsg.setOnClickListener {
            val textMessage = binding.etMessage.text
            if (textMessage.isNotEmpty() && textMessage.isNotBlank()) {
                val fullName = getString(
                    R.string.person_full_name,
                    contactInfo.firstName,
                    contactInfo.lastName
                )
                val phone = getString(
                    R.string.mobile_with_code,
                    contactInfo.countryCode,
                    contactInfo.mobileNumber.toString()
                )
                val currentDateTime = System.currentTimeMillis()
                val message =
                    Message(0, fullName, phone, message = textMessage.toString(), currentDateTime)
                viewModel.saveMessageInDB(message)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) =
            ComposeMessageFragment().apply {
                arguments = bundle
            }
    }
}