package com.example.messagingapp.viewModels

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel() : ViewModel() {

    val navigateToComposeMessageFragment = MutableLiveData<Bundle>()

}