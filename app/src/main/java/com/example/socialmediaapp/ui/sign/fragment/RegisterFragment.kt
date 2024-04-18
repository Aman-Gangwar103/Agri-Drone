package com.example.socialmediaapp.ui.sign.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.socialmediaapp.R
import com.example.socialmediaapp.databinding.FragmentRegisterBinding
import com.example.socialmediaapp.helpers.MyValidation
import com.example.socialmediaapp.models.User
import com.example.socialmediaapp.ui.sign.ViewModelSignUser
import com.example.socialmediaapp.utils.Status
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {



    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!




    var uri : Uri ?=null


    @Inject
    lateinit var mycontext: Context

    @Inject
    lateinit var auth: FirebaseAuth

    private val viewModel by viewModels<ViewModelSignUser>()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.regBtnRegister.setOnClickListener {
            val name = binding.inputTextLayoutName.editText?.text.toString()
            val bio = binding.inputTextLayoutBio.editText?.text.toString()
            val email = binding.inputTextLayoutEmail.editText?.text.toString()
            val password = binding.inputTextLayoutPassword.editText?.text.toString()

            if (valid()) {
                val user = User(
                    name,
                    bio,
                    email, "", "", "", "")


                viewModel.createUser(email, password)
                viewModel.createUserLiveData.observe(viewLifecycleOwner){
                    when(it.status){
                        Status.LOADING->{
                            binding.progress.visibility = View.VISIBLE
                        }
                        Status.SUCCESS->{
                            binding.progress.visibility = View.INVISIBLE

                            // Show a success message if needed
                            Toast.makeText(activity, "Registration successful!", Toast.LENGTH_SHORT).show()

                            // Navigate to another fragment
                            findNavController().navigate(
                                R.id.action_registerFragment_to_loginFragment
                            )

                        }
                        Status.ERROR->{
                            binding.progress.visibility = View.INVISIBLE
                            Toast.makeText(activity, ""+it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        binding.regBacktologin.setOnClickListener {
            findNavController().navigate(
                R.id.action_registerFragment_to_loginFragment
            )
        }
    }

    private fun valid():Boolean{
        val bio = binding.inputTextLayoutBio.editText?.text.toString()

        if (!MyValidation.validateName(requireContext(),binding.inputTextLayoutName)){
            return false
        }else if (bio.isEmpty()){
            binding.inputTextLayoutBio.isHelperTextEnabled = true
            binding.inputTextLayoutBio.helperText = "Require*"
            return false
        }else if (!MyValidation.isValidEmail(requireContext(),binding.inputTextLayoutEmail)){
            return false
        }else if (!MyValidation.validatePass(requireContext(),binding.inputTextLayoutPassword)){
            return false
        }
        else{
            return true
        }
    }


}