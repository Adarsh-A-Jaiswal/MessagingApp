package com.example.messagingapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.R
import com.example.messagingapp.common.Utils
import com.example.messagingapp.databinding.MessageViewBinding
import com.example.messagingapp.modelsClasses.Message

class MessageAdapter(
    private val context: Context,
    private val messageList: ArrayList<Message>,
    private val onItemClick: (Message, Int) -> Unit
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
        val date = Utils.getLocalDateTime(message.timeStamp)
        val month =
            date?.month?.name?.take(3)?.lowercase()?.replaceFirstChar { it.titlecase() }
        holder.apply {
            personNameTv.text = message.name
            subMessageTV.text = message.message
            dateTimeTV.text = context.getString(
                R.string.dateFormat, date?.dayOfMonth.toString(), month, date?.year.toString()
            )
            root.setOnClickListener {
                onItemClick.invoke(message, adapterPosition)
            }
        }
    }

}