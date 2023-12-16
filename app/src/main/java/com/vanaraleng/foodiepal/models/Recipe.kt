package com.vanaraleng.foodiepal.models

data class Recipe(
    val name: String,
    val cookingTime: String,
    var rating: Int,
    val image: Int
) {
    override fun toString(): String {
        return name
    }
}
