package com.example.aesutiltestapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aesutiltestapp.util.Event

class MainViewModel: ViewModel() {
    private val _encryptEvent = MutableLiveData<Event<Any>>()
    val encryptEvent: LiveData<Event<Any>>
        get() = _encryptEvent

    private val _decryptEvent = MutableLiveData<Event<Any>>()
    val decryptEvent: LiveData<Event<Any>>
        get() = _decryptEvent


    fun clickEncryptButton() {
        _encryptEvent.value = Event(Any())
    }

    fun clickDecryptButton() {
        _decryptEvent.value = Event(Any())
    }
}