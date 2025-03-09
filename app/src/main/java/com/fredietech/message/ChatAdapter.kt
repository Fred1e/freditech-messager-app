package com.fredietech.message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fredietech.message.databinding.ItemMessageBinding

class ChatAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val msg = messages[position]
        holder.binding.messageTextView.text = msg.text
        holder.binding.statusTextView.text = if (msg.delivered) "Delivered" else "Sending..."
        if (msg.archived) {
            holder.binding.root.alpha = 0.5f // visually mark archived messages
        } else {
            holder.binding.root.alpha = 1.0f
        }
    }

    override fun getItemCount(): Int = messages.size
}
