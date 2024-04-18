package com.example.socialmediaapp.firebase

import com.google.firebase.auth.FirebaseAuth

object MyFirebase {

    val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

}