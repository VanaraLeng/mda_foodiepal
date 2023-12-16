package com.vanaraleng.foodiepal

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.vanaraleng.foodiepal.data.Constant
import com.vanaraleng.foodiepal.data.Mock
import com.vanaraleng.foodiepal.databinding.ActivityLoginBinding
import com.vanaraleng.foodiepal.models.User
import com.vanaraleng.foodiepal.utils.PreferenceUtil

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener { performLogin(it) }

        binding.signup.setOnClickListener { performSignUp(it) }
    }

    private fun performLogin(view: View) {
        view.hideKeyboard()
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()

        if (username.isBlank() || password.isBlank())  {
            Snackbar.make(binding.login,
                getString(R.string.error_incorrect_input), Snackbar.LENGTH_LONG).show()
            return
        }

        val user: User? = Mock.mockUsers.firstOrNull {
            it.username == username
        }

        if (user == null) {
            // User not found
            Snackbar.make(binding.login,
                getString(R.string.error_user_not_found), Snackbar.LENGTH_LONG).show()
            return
        }
        if (user.password != password) {
            // Wrong password
            Snackbar.make(binding.login,
                getString(R.string.error_incorrect_password), Snackbar.LENGTH_LONG).show()
            return
        }

        val pref = PreferenceUtil(applicationContext)
        pref.setString(Constant.username, user.username)
        pref.setBool(Constant.isLoggedIn, true)

        // correct password, finish activity
        finish()
    }

    private fun performSignUp(view: View) {
        view.hideKeyboard()
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()

        if (username.isBlank() || password.isBlank())  {
            Snackbar.make(binding.login,
                getString(R.string.error_incorrect_input), Snackbar.LENGTH_LONG).show()
            return
        }

        val user: User? = Mock.mockUsers.firstOrNull {
            it.username == username
        }

        if (user != null) {
            // User not found
            Snackbar.make(binding.signup,
                getString(R.string.error_user_existed), Snackbar.LENGTH_LONG).show()
            return
        }

        val newUser = User("", username, password)
        Mock.mockUsers.add(newUser)

        Snackbar.make(binding.signup,
            getString(R.string.login_user_signed_up_successfully), Snackbar.LENGTH_LONG).show()
    }
}