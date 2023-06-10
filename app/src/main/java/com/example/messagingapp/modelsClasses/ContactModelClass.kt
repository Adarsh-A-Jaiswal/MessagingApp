package com.example.messagingapp.modelsClasses

data class Contacts(
    val contacts: ArrayList<Contact>
)

data class Contact(
    var firstName: String? = null,
    var lastName: String? = null,
    var mobileNumber: Long? = null,
    var countryCode: String? = null
)