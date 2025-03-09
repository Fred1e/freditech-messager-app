package com.fredietech.message

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fredietech.message.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ChatAdapter
    private val messages = mutableListOf<Message>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // Load saved theme preference before setting content view
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        val isDarkTheme = sharedPreferences.getBoolean("dark_theme", false)
        if (isDarkTheme) {
            setTheme(R.style.Theme_FredieTechMessage_Dark)
        } else {
            setTheme(R.style.Theme_FredieTechMessage_Light)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ChatAdapter(messages)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.sendButton.setOnClickListener {
            val text = binding.messageEditText.text.toString().trim()
            if (text.isNotEmpty()) {
                val msg = Message(text = text, delivered = false, archived = false)
                messages.add(msg)
                adapter.notifyItemInserted(messages.size - 1)
                binding.recyclerView.scrollToPosition(messages.size - 1)
                binding.messageEditText.text.clear()

                // Simulate message delivery after 1 second
                binding.recyclerView.postDelayed({
                    msg.delivered = true
                    adapter.notifyItemChanged(messages.indexOf(msg))
                }, 1000)
            }
        }

        binding.archiveButton.setOnClickListener {
            // Archive all messages (for demo, we mark them as archived)
            messages.forEach { it.archived = true }
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // A simple menu to switch themes
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_theme -> {
                val isDark = sharedPreferences.getBoolean("dark_theme", false)
                sharedPreferences.edit().putBoolean("dark_theme", !isDark).apply()
                recreate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
