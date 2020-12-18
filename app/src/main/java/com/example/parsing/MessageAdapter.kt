package com.example.parsing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.parsing.models.Message

class MessageAdapter(
    private val context: Context,
    private val dataSource: List<Message?>,
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if(convertView == null){
            // Inflate view with list_item_message.xml
            view = inflater.inflate(R.layout.list_item_message, parent, false)
            holder = ViewHolder()
            holder.messageTextView = view.findViewById(R.id.message_list_title)
            holder.authorTextView = view.findViewById(R.id.message_list_author)
            holder.followerTextView = view.findViewById(R.id.message_list_followers)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // Put fill view with appropriate message
        val message = getItem(position) as Message
        holder.messageTextView.text = message.text
        holder.authorTextView.text = message.user.name
        holder.followerTextView.text = message.user.followers
        return view
    }

    override fun getItem(position: Int): Any {
        return dataSource[position] as Message
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    private class ViewHolder {
        lateinit var messageTextView: TextView
        lateinit var authorTextView: TextView
        lateinit var followerTextView: TextView
    }

}