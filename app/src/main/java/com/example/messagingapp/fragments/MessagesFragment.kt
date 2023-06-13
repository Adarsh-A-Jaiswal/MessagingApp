package com.example.messagingapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.activities.InfoActivity
import com.example.messagingapp.adapters.MessageAdapter
import com.example.messagingapp.common.Constants
import com.example.messagingapp.databinding.FragmentMessagesBinding
import com.example.messagingapp.modelsClasses.Message
import com.example.messagingapp.viewModels.ContactViewModel
import com.google.gson.Gson

class MessagesFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ContactViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MessageAdapter
    private val messageList = ArrayList<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        recyclerView = binding.rvMessage
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadMessagesFromDB()
        setAdapter()
        setObservers()
    }

    private fun setAdapter() {
        adapter = MessageAdapter(requireContext(), messageList, ::navigateToComposeFrag)
        recyclerView.adapter = adapter
    }

    private fun setObservers() {
        viewModel.messages.observe(viewLifecycleOwner) { messageList ->
            messageList?.let {
                updateList(it)
                if (it.isEmpty() && messageList.isEmpty()) {
                    binding.rvMessage.isVisible = false
                    binding.tvNoMessages.isVisible = true
                } else {
                    binding.rvMessage.isVisible = true
                    binding.tvNoMessages.isVisible = false
                }
            }
        }

    }

    private fun navigateToComposeFrag(message: Message, position: Int) {
        val bundle = Bundle()
        bundle.apply {
            putString(Constants.MESSAGE_JSON_KEY, Gson().toJson(message))
            putString(Constants.MESSAGE_TAB_KEY, Constants.MESSAGE)
        }
        val intent = Intent(requireContext(), InfoActivity::class.java).apply {
            putExtra(Constants.BUNDLE_KEY, bundle)
        }

        requireActivity().startActivity(intent)
    }

    private fun updateList(list: ArrayList<Message>) {
        messageList.addAll(list)
        adapter.notifyDataSetChanged()
    }
}