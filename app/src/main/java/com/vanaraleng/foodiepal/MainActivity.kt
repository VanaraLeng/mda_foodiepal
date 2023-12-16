package com.vanaraleng.foodiepal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vanaraleng.foodiepal.data.Mock
import com.vanaraleng.foodiepal.databinding.ActivityMainBinding
import com.vanaraleng.foodiepal.models.Recipe
import com.vanaraleng.foodiepal.models.TabItem
import com.vanaraleng.foodiepal.utils.PreferenceUtil


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val menus = arrayOf(
        TabItem("Recipes",  1),
        TabItem("Meal Planner", 2),
        TabItem("Blog",3),
        TabItem("Contact", 4),
        TabItem("About Me", 5)
    )

    val recipes = Mock.mockRecipes.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setupPagerAdapter()


        binding.navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_recipes -> changePage(0)
                R.id.action_planner -> changePage(1)
                R.id.action_blog -> changePage(2)
                else -> false
            }
        }
    }

    private fun changePage(page: Int): Boolean {
        binding.content.viewPager.currentItem = page
        return true
    }

    override fun onPostResume() {
        super.onPostResume()

        // Show login user when there is no session
        val isUserLogin = PreferenceUtil(applicationContext).getBool("isLoggedIn")
        if (!isUserLogin) {
            showLoginActivity()
        }
    }

    private fun showLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun setupPagerAdapter() {
        val pagerAdapter = PagerAdapter(this)
        val pager = binding.content.viewPager
        pager.adapter = pagerAdapter
        binding.content.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        TabLayoutMediator(binding.content.tabLayout,
            binding.content.viewPager) { tab, position ->
                tab.text = menus[position].title
            }
        .attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> logout()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout(): Boolean {
        val preferenceUtil = PreferenceUtil(this)
        preferenceUtil.setBool("isLoggedIn", false)
        preferenceUtil.setString("user", null)
        showLoginActivity()
        return true
    }


    private fun addRecipe(name: String, time: String) {
        val recipe = Recipe(name, time, 0, R.drawable.bibimbab)
        recipes.add(recipe)
        Toast.makeText(this,"Recipe added", Toast.LENGTH_LONG).show()
    }
}