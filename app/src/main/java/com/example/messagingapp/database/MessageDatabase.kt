package com.example.messagingapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.messagingapp.modelsClasses.Message

@Database(entities = [Message::class], version = 1)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: MessageDatabase? = null

        fun getDataBase(context: Context): MessageDatabase {
            if (INSTANCE == null) {
                INSTANCE = synchronized(this) {
                    Room.databaseBuilder(
                        context.applicationContext,
                        MessageDatabase::class.java,
                        "messageDB"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}