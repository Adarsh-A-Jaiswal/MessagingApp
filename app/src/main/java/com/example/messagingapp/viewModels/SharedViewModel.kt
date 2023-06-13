package com.example.messagingapp.viewModels

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.messagingapp.modelsClasses.Message
import com.example.messagingapp.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel(private val application: Application) : AndroidViewModel(application) {

    val navigateToComposeMessageFragment = MutableLiveData<Bundle>()

    fun saveMessageInDB(message: Message) {
        viewModelScope.launch(Dispatchers.IO) {
            MessageRepository(application).saveMessage(message)
        }
    }

}