package com.example.parsing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.BaseAdapter
import android.widget.ListView
import com.example.parsing.models.Message

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById<ListView>(R.id.message_list_view)

        var messageList = Message.getMessageFromFile("messages.json", this)

        if(messageList != null) {
            val adapter = MessageAdapter(this, messageList)
            listView.adapter = adapter
        } else {
            println("message list is null")
        }
    }
}