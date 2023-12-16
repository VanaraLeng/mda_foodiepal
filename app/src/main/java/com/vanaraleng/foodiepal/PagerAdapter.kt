package com.vanaraleng.foodiepal

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vanaraleng.foodiepal.views.AboutFragment
import com.vanaraleng.foodiepal.views.BlogFragment
import com.vanaraleng.foodiepal.views.ContactFragment
import com.vanaraleng.foodiepal.views.MealPlanerFragment
import com.vanaraleng.foodiepal.views.RecipeFragment

class PagerAdapter(private val activity: MainActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return activity.menus.count()
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RecipeFragment()
            1 -> MealPlanerFragment()
            2 -> BlogFragment()
            3 -> ContactFragment()
            4 -> AboutFragment()
            else -> Fragment()
        }
    }
}