package com.vanaraleng.foodiepal.models

import java.util.Date

data class MealPlan(
    val date: Date,
    val breakfast: Recipe?,
    val lunch: Recipe?,
    val dinner: Recipe?
)
