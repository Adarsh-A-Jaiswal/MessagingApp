package com.example.messagingapp.modelsClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name: String,
    val phone: String,
    val message: String,
    val timeStamp: Long
)
