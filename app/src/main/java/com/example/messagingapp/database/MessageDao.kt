package com.example.messagingapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.messagingapp.modelsClasses.Message

@Dao
interface MessageDao {

    @Insert
    suspend fun insertMessage(message: Message)

    @Delete
    suspend fun deleteMessage(message: Message)

    @Query("SELECT * FROM message WHERE phone = :phoneWithCode")
    suspend fun getMessagesFor(phoneWithCode: String): List<Message>

    @Query("SELECT id,name,phone,message,MAX(timeStamp) AS timeStamp FROM message GROUP BY phone ORDER BY id DESC")
    suspend fun getAllMessages(): List<Message>
}