package com.example.socialmediaapp.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.socialmediaapp.adapter.AdapterPost
import com.example.socialmediaapp.databinding.FragmentHomeBinding
import com.example.socialmediaapp.models.Post
import com.example.socialmediaapp.ui.main.ViewModelMain
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var homeAdapter: AdapterPost
    private val viewModel by viewModels<ViewModelMain>()
    private var postList: ArrayList<Post> = ArrayList()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}