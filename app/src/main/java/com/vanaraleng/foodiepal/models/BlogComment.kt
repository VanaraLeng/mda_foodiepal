package com.vanaraleng.foodiepal.models

data class BlogComment(
    val username: String,
    val text: String
) {
    override fun toString(): String {
        return "@$username: $text"
    }
}
