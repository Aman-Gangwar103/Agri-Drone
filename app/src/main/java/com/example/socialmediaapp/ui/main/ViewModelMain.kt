package com.example.socialmediaapp.ui.main

import android.net.Uri
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmediaapp.models.Post
import com.example.socialmediaapp.models.User
import com.example.socialmediaapp.repository.Repository
import com.example.socialmediaapp.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMain  @Inject constructor(
    private val repository: Repository,
    val auth: FirebaseAuth,
    ) : ViewModel() {



}