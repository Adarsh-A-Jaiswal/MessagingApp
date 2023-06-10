package com.example.messagingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.adapters.ContactListAdapter
import com.example.messagingapp.common.Utils
import com.example.messagingapp.databinding.FragmentContactsBinding
import com.example.messagingapp.modelsClasses.Contact
import com.example.messagingapp.modelsClasses.Contacts
import com.example.messagingapp.viewModels.ContactViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private var contactList = ArrayList<Contact>()
    private lateinit var viewModel: ContactViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        viewModel.loadContactsFromAssets(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        //initializing recycleView
        recyclerView = binding.rvContacts
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()

        setObservers()

    }

    /**
     * Method for initializing adapter with ContactListAdapter
     */
    private fun setAdapter() {
        //initializing adapter with ContactListAdapter and passing empty Contact list
        adapter = ContactListAdapter(requireContext(), contactList)
        recyclerView.adapter = adapter
    }

    /**
     * Method to observe live data present in view model
     */
    private fun setObservers() {
        // observing contactsLiveData to Update list
        viewModel.contactsLiveData.observe(viewLifecycleOwner) { contacts ->
            contacts?.let {
                updateContactList(it)
            }
        }
        // observing errorLiveData to show Error if any
        viewModel.errorLiveData.observe(viewLifecycleOwner) { errorString ->
            errorString?.let {
                //calling method which is in Utils to show Toast Message
                Utils.showToast(requireContext().applicationContext, it)
            }
        }
    }

    private fun updateContactList(contacts: Contacts) {
        contactList.addAll(contacts.contacts)
        adapter.notifyDataSetChanged()
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = ContactsFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}