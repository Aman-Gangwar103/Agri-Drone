package com.example.socialmediaapp.ui.sign.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.socialmediaapp.R
import com.example.socialmediaapp.helpers.MyValidation
import com.example.socialmediaapp.ui.main.MainActivity
import com.example.socialmediaapp.ui.sign.ViewModelSignUser
import com.example.socialmediaapp.utils.Status
import com.example.socialmediaapp.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleSignInClient: GoogleSignInClient
    private val viewModel by viewModels<ViewModelSignUser>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtnLogIn.setOnClickListener {
            val email: String = binding.inputTextLayoutEmail.editText!!.text.toString()
            val password: String = binding.inputTextLayoutPassword.editText!!.text.toString()

            if (MyValidation.isValidEmail(requireContext(), binding.inputTextLayoutEmail)
                && MyValidation.validatePass(requireContext(), binding.inputTextLayoutPassword)) {

                viewModel.signInWithEmailAndPassword(email, password)
                viewModel.successToLoginLiveData.observe(viewLifecycleOwner) {
                    when(it.status){
                        Status.LOADING -> {
                            binding.progress.visibility = View.VISIBLE
                        }

                        Status.SUCCESS -> {
                            binding.progress.visibility = View.GONE
                            startActivity(Intent(activity, MainActivity::class.java))
                            activity?.finish()
                        }

                        Status.ERROR -> {
                            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.loginBtnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginForget.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
        }

        binding.googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            if (task != null && task.isSuccessful) {
                val account = task.result
                firebaseAuthWithGoogle(account!!)
            } else {
                // Google Sign In failed
                Toast.makeText(activity, "Google Sign In failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    val user = FirebaseAuth.getInstance().currentUser
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                    // Navigate to main activity or perform any other operation
                } else {
                    // Sign in failed
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(activity, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        googleSignInClient.signOut().addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Google sign out successful")
                // Navigate to login or perform any other operation
            } else {
                Log.e(TAG, "Google sign out failed", task.exception)
                // Handle sign-out failure
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "LoginFragment"
        private const val RC_SIGN_IN = 9001
    }
}
