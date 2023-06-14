package com.example.messagingapp.fragments

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
import com.example.messagingapp.adapters.MessageAdapter
import com.example.messagingapp.common.Utils
import com.example.messagingapp.databinding.FragmentMessagesBinding
import com.example.messagingapp.modelsClasses.Message
import com.example.messagingapp.viewModels.ContactViewModel

class MessagesFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ContactViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MessageAdapter
    private val messageList = ArrayList<Message>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        recyclerView = binding.rvMessage
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadMessagesFromDB()
        setAdapter()
        setObservers()
    }

    private fun setAdapter() {
        adapter = MessageAdapter(requireContext(), messageList, ::showToast)
        recyclerView.adapter = adapter
    }

    private fun showToast(message: Message) {
        val toastString = "${message.name} Message: ${message.message}"
        Utils.showToast(requireContext(), toastString)
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

    private fun updateList(list: ArrayList<Message>) {
        messageList.clear()
        messageList.addAll(list)
        adapter.notifyDataSetChanged()
    }
}