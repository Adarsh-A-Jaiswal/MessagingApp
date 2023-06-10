package com.example.messagingapp.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messagingapp.modelsClasses.Contacts
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.nio.charset.Charset

class ContactViewModel() : ViewModel() {

    //mutable live data for contacts
    private val _contacts = MutableLiveData<Contacts?>()

    // getter for _contacts var
    val contactsLiveData: LiveData<Contacts?> get() = _contacts

    //mutable live data for error
    private val _errorLiveData = MutableLiveData<String?>()

    // getter for _errorLiveData var
    val errorLiveData: LiveData<String?> get() = _errorLiveData

    /**
     * Method to load the Contacts from the Assets file and post to liveData
     */
    fun loadContactsFromAssets(appContext: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //Here we are calling a Method which is returning the JSON object
                val jsonString = async { getJSONFromAssets(appContext = appContext) }
                jsonString.await()?.let {
                    //Here we are converting JSON object to Contacts
                    val contacts = Gson().fromJson(it, Contacts::class.java)
                    //Here we are posting _contacts to live data
                    _contacts.postValue(contacts)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                //Here we are posting error to _errorLiveData
                _errorLiveData.postValue(e.toString())
            }
        }
    }

    /**
     * Method to load the JSON from the Assets file and return the object
     */
    private fun getJSONFromAssets(appContext: Context): String? {
        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val myUsersJSONFile = appContext.assets.open("data.json")
//            json = inputStream.bufferedReader().use { it.readText() }
            val size = myUsersJSONFile.available()
            val buffer = ByteArray(size)
            myUsersJSONFile.read(buffer)
            myUsersJSONFile.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            _errorLiveData.postValue(ex.toString())
            return null
        }
        return json
    }

}