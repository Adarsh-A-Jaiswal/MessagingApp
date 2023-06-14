package com.example.messagingapp.viewModels

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.messagingapp.common.Constants
import com.example.messagingapp.common.Utils
import com.example.messagingapp.messageService.Api
import com.example.messagingapp.modelsClasses.Message
import com.example.messagingapp.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedViewModel(private val application: Application) : AndroidViewModel(application) {

    val navigateToComposeMessageFragment = MutableLiveData<Bundle>()

    private fun saveMessageInDB(message: Message) {
        viewModelScope.launch(Dispatchers.IO) {
            MessageRepository(application).saveMessage(message)
        }
    }

    fun sendMessage(message: Message) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = Api.getInstance().postMessage(
                    "+14302456291",
                    Constants.TO_PHONE,
                    message.message
                )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Utils.showToast(
                            application.applicationContext,
                            "Message Sent Successfully"
                        )
                        saveMessageInDB(message)
                    } else {
                        Log.d("TAG", "response == $response")
                        Utils.showToast(
                            application.applicationContext,
                            "Message not Sent to ${message.name}"
                        )
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Utils.showToast(
                        application.applicationContext,
                        " Message not sent to ${message.name} \n $e"
                    )
                    Log.d("TAG", "e == $e")
                }
            }
        }
    }
}