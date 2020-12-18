package com.example.parsing.models

import android.content.Context
import android.util.JsonReader

import android.util.JsonToken
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class Message(
    val id: Int,
    val text: String,
    val user: User
) {

    companion object{

        @Throws(IOException::class)
        fun getMessageFromFile(filename: String, context: Context) : List<Message?>?{
            // Load Data
            var messageList: List<Message?>?

            try {
                val inputStream = context.assets.open(filename)
                messageList = readJsonStream(inputStream)
            } catch ( ex: java.io.IOException){
                ex.printStackTrace()
                return null
            }

            return messageList
        }

        @Throws(IOException::class)
        fun readJsonStream(`in`: InputStream?): List<Message?>? {
            val reader = JsonReader(InputStreamReader(`in`, "UTF-8"))
            return try {
                readMessagesArray(reader)
            } finally {
                reader.close()
            }
        }

        @Throws(IOException::class)
        fun readMessagesArray(reader: JsonReader): List<Message?>? {
            val messages: MutableList<Message?> =
                ArrayList()
            reader.beginArray()
            while (reader.hasNext()) {
                messages.add(readMessage(reader))
            }
            reader.endArray()
            return messages
        }

        @Throws(IOException::class)
        fun readMessage(reader: JsonReader): Message? {
            var id: Long = -1
            var text: String? = null
            var user: User? = null
            reader.beginObject()
            while (reader.hasNext()) {
                val name = reader.nextName()
                if (name == "id") {
                    id = reader.nextLong()
                } else if (name == "text") {
                    text = reader.nextString()
                } else if (name == "user") {
                    user = readUser(reader)
                } else {
                    reader.skipValue()
                }
            }
            reader.endObject()
            return Message(id.toInt(), text!!, user!!)
        }

        @Throws(IOException::class)
        fun readDoublesArray(reader: JsonReader): List<Double?>? {
            val doubles: MutableList<Double?> =
                ArrayList()
            reader.beginArray()
            while (reader.hasNext()) {
                doubles.add(reader.nextDouble())
            }
            reader.endArray()
            return doubles
        }

        @Throws(IOException::class)
        fun readUser(reader: JsonReader): User? {
            var username: String? = null
            var followersCount = -1
            reader.beginObject()
            while (reader.hasNext()) {
                val name = reader.nextName()
                if (name == "name") {
                    username = reader.nextString()
                } else if (name == "followers_count") {
                    followersCount = reader.nextInt()
                } else {
                    reader.skipValue()
                }
            }
            reader.endObject()
            return User(username!!, followersCount)
        }
    }

}