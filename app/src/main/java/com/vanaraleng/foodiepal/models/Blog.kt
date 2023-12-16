package com.vanaraleng.foodiepal.models

data class Blog(
    val username: String,
    val postedTime: String,
    val title: String,
    val text: String,
    val comments: MutableList<BlogComment>
)
