package com.fredietech.message

data class Message(
    val text: String,
    var delivered: Boolean,
    var archived: Boolean
)
