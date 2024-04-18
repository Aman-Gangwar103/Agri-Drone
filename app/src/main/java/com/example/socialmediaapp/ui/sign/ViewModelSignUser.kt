package com.example.socialmediaapp.ui.sign

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmediaapp.models.User
import com.example.socialmediaapp.repository.RepositoryUser
import com.example.socialmediaapp.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewModelSignUser @Inject constructor(
    private val repository: RepositoryUser,
    val context: Context
) : ViewModel() {


    fun createUser(email: String, password: String, uri: Uri, user: User) {
        viewModelScope.launch {
            repository.createUser(email, password,uri, user)
        }
    }


    var successToLoginLiveData = MutableLiveData<Resource<Boolean>>()
    fun signInWithEmailAndPassword(email: String, password: String){
        viewModelScope.launch {
            successToLoginLiveData=repository.signInWithEmailAndPassword(email, password)
        }
    }





    var createUserLiveData = MutableLiveData<Resource<FirebaseUser>>()
    fun createUser(email: String, password: String){
        viewModelScope.launch {
            createUserLiveData=repository.createUser(email, password)
        }
    }


    var setUserDataInfoOnDatabaseLiveData = MutableLiveData<Resource<Boolean>>()
    fun setUserDataInfoOnDatabase(user: User){
        viewModelScope.launch {
            setUserDataInfoOnDatabaseLiveData=repository.setUserDataInfoOnDatabase(user)
        }
    }



}