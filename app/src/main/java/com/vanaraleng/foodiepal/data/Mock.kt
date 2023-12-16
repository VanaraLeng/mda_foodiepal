package com.vanaraleng.foodiepal.data

import com.vanaraleng.foodiepal.R
import com.vanaraleng.foodiepal.models.Blog
import com.vanaraleng.foodiepal.models.BlogComment
import com.vanaraleng.foodiepal.models.Info
import com.vanaraleng.foodiepal.models.MealPlan
import com.vanaraleng.foodiepal.models.Recipe
import com.vanaraleng.foodiepal.models.User
import java.util.Date

object Mock {

    val mockUsers = mutableListOf(
        User("admin", "vanara.leng", "123"),
        User("Administrator", "admin", "123"),
    )

    val mockRecipes =  listOf(
        Recipe("Korean Bibimbab", "15 mins", 5, R.drawable.bibimbab),
        Recipe("Sprinkling Cocoa Powder on Tiramisu Trifles Free Stock Photo", "20 mins", 4, R.drawable.tiramisu),
        Recipe("Homemade Pastry Cinnamon Swirls", "20 mins", 4, R.drawable.cinnamon_swirls),
    )

    val mockMealPlans = listOf(
        MealPlan(
            Date(),
            mockRecipes[1],
            mockRecipes[2],
            null)
    )

    val mockBlogs = listOf(
        Blog(
            "vanara.leng",
            "10pm",
            "How to make Kimchi",
            "Kimchi is made by cutting vegetables into slices or strips, massaging them with salt to create a brine, adding spices, then densely packing the mixture into a jar and leaving it to ferment for at least a week, but often longer, at room temperature",
            mutableListOf(
                BlogComment(
                    "ronald",
                    "Good one"
                )
            )
        )
    )

    val infos = listOf(
        Info("Philosophy", "Food is gift from heaven"),
        Info("Favorite foods", "Kimchi Soup, Japanese Udon, Korean Barbeque and Chinese hotpot")
    )

    val phoneNumber = "5629558583"
    val email = "vanara.leng@miu.edu"
}