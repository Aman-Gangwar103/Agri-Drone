package com.example.socialmediaapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Profile : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        auth = FirebaseAuth.getInstance()

        // Get the current user
        val user: FirebaseUser? = auth.currentUser

        // Find views in the layout
        val displayNameTextView: TextView = findViewById(R.id.display_name_text_view)
        val emailTextView: TextView = findViewById(R.id.email_text_view)

        // Display user information
        displayNameTextView.text = "WELCOME TO AGRI DRONE APP ${user?.displayName}"
        emailTextView.text = "E-MAIL ID: ${user?.email}"
    }
}