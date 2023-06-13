package com.example.messagingapp.repository

import android.content.Context
import com.example.messagingapp.database.MessageDatabase
import com.example.messagingapp.modelsClasses.Message

class MessageRepository(context: Context) {

    private val database = MessageDatabase.getDataBase(context)

    suspend fun getMessages(): List<Message> {
        return database.messageDao().getAllMessages()
    }

    suspend fun getMessagesFor(phoneWithCode: String): List<Message> {
        return database.messageDao().getMessagesFor(phoneWithCode)
    }

    suspend fun saveMessage(message: Message) {
        database.messageDao().insertMessage(message = message)
    }

    suspend fun deleteMessage(message: Message) {
        database.messageDao().deleteMessage(message = message)
    }

}