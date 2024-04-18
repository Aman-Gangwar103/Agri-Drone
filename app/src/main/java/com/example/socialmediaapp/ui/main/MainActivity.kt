package com.example.socialmediaapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import com.example.socialmediaapp.CHATBOT
import com.example.socialmediaapp.MapsWindow
import com.example.socialmediaapp.Profile
import com.example.socialmediaapp.R
import com.example.socialmediaapp.databinding.ActivityMainBinding
import com.example.socialmediaapp.developers
import com.example.socialmediaapp.earth
import com.example.socialmediaapp.ui.sign.LoginAndSignUpActivity
import com.example.socialmediaapp.weather
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            // If not logged in, navigate to login screen
            startActivity(Intent(this, LoginAndSignUpActivity::class.java))
            finish() // Close the MainActivity
        }

        // Initialize views and set click listeners
        binding.profBtnSetting.setOnClickListener {
            showPopupMenu()
        }

        val mapsCard: CardView = findViewById(R.id.maps_card)
        mapsCard.setOnClickListener {
            // Handle click action here, for example, navigate to the maps screen
            try {
                val intent = Intent(this, MapsWindow::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val weatherCard: CardView = findViewById(R.id.weather_card)
        weatherCard.setOnClickListener {
            try {
                val intent = Intent(this, weather::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val chatbotButton: Button = findViewById(R.id.drone_tracker_button)
        chatbotButton.setOnClickListener {
            // Handle click action here, for example, navigate to the new interface activity
            val intent = Intent(this, CHATBOT::class.java)
            startActivity(intent)
        }

        val ProfileButton: Button = findViewById(R.id.profile_btn)
        ProfileButton.setOnClickListener {
            // Handle click action here, for example, navigate to the new interface activity
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }


        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.earth -> {
                    // Open EarthActivity
                    val intent = Intent(this, earth::class.java)
                    startActivity(intent)
                    true
                }

                R.id.developers -> {
                    // Open EarthActivity
                    val intent = Intent(this, developers::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }

    private fun showPopupMenu() {
        // Implement the logic for showing the popup menu.
        val popupMenu = PopupMenu(this, binding.profBtnSetting)
        popupMenu.menuInflater.inflate(R.menu.pop_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.logout -> {
                    signOut()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun signOut() {
        // Sign out from Firebase Authentication
        auth.signOut()

        // Navigate to the login screen
        startActivity(Intent(this, LoginAndSignUpActivity::class.java))
        finish()
    }


}


