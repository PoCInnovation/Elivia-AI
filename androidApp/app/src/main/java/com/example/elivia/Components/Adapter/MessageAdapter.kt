package com.example.elivia.Components.Adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elivia.Models.Messages
import com.example.elivia.R

class MessageAdapter (val context: Context) : RecyclerView.Adapter<MessageViewHolder>() {
    private val messages: ArrayList<Messages> = ArrayList()

    private val VIEW_TYPE_MY_MESSAGE = 1
    private val VIEW_TYPE_OTHER_MESSAGE = 2

    fun addMessage(message: Messages){
        messages.add(message)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages.get(position)

        return if(message.user == "test") {
            VIEW_TYPE_MY_MESSAGE
        } else {
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

    inner class MyMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.findViewById(R.id.txtMyMessage);
        private var timeText: TextView = view.findViewById(R.id.txtMyMessageTime);

        override fun bind(message: Messages) {
            messageText.text = message.text
            timeText.text = DateUtils.formatDateTime(context, message.time,0);
        }
    }

    inner class ResponceMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.findViewById(R.id.txtOtherMessage);
        private var timeText: TextView = view.findViewById(R.id.txtOtherMessageTime);

        override fun bind(message: Messages) {
            messageText.text = message.text
            timeText.text = DateUtils.formatDateTime(context, message.time,0);
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if(viewType == VIEW_TYPE_MY_MESSAGE) {
            MyMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.my_messages, parent, false))
        } else {
            ResponceMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.responce_messages, parent, false))
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages.get(position)

        holder?.bind(message)
    }

}

open class MessageViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message: Messages) {}
}