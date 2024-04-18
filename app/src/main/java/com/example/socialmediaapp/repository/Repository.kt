package com.example.socialmediaapp.repository


import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.socialmediaapp.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.StorageReference
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage

import javax.inject.Inject

class Repository @Inject constructor(
    var refDatabase: DatabaseReference,
    private var refStorage: StorageReference,
    private var auth: FirebaseAuth,
    private var firebaseMessaging: FirebaseMessaging,
    private var context: Context
) {

    private val languageIdentifierLiveData=MutableLiveData<Resource<String>>()
    private val languageIdentifier = LanguageIdentification.getClient()

}

