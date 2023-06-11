package com.example.messagingapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.R
import com.example.messagingapp.databinding.ContactViewBinding
import com.example.messagingapp.modelsClasses.Contact

class ContactListAdapter(
    private val context: Context,
    private val contactList: ArrayList<Contact>,
    private val onItemClick: (Contact, Int) -> Unit
) :
    RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(binding: ContactViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val personNameTv = binding.tvPersonName
        val personMobileTv = binding.tvMobileNum
        val containerTv = binding.tvContainer
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ContactViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = contactList.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]

        holder.personNameTv.text = context.getString(
            R.string.person_full_name,
            contact.firstName.toString(),
            contact.lastName
        )

        holder.personMobileTv.text = context.getString(
            R.string.mobile_with_code,
            contact.countryCode,
            contact.mobileNumber.toString()
        )

        holder.containerTv.setOnClickListener {
            onItemClick.invoke(contact, holder.adapterPosition)
        }
    }

}