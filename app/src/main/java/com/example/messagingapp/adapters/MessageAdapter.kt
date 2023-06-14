package com.example.messagingapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.databinding.MessageViewBinding
import com.example.messagingapp.modelsClasses.Message
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MessageAdapter(
    private val context: Context,
    private val messageList: ArrayList<Message>,
    private val onItemClick: (Message) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(binding: MessageViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val personNameTv = binding.tvPersonFullName
        val subMessageTV = binding.tvMessage
        val dateTimeTV = binding.tvDateTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            MessageViewBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int = messageList.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageList[position]
        val date = Date(message.timeStamp)
        val timeDate = SimpleDateFormat("dd MMM yyyy \n hh:mm a", Locale.getDefault())
        holder.apply {
            personNameTv.text = message.name
            subMessageTV.text = message.message
            dateTimeTV.text = timeDate.format(date)
            root.setOnClickListener {
                onItemClick.invoke(message)
            }
        }
    }

}