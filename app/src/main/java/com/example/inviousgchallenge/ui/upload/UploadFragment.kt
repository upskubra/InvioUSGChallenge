package com.example.inviousgchallenge.ui.upload

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.inviousgchallenge.databinding.FragmentUploadBinding
import com.example.inviousgchallenge.ui.activity.ActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UploadFragment : Fragment() {
    private lateinit var binding: FragmentUploadBinding
    private val uploadViewModel: UploadViewModel by viewModels()
    private val sessionViewModel by activityViewModels<ActivityViewModel>()
    private lateinit var imageUri: Uri
    private val args: UploadFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUploadBinding.inflate(inflater)

        args.catUrl?.let {
            Glide.with(this).load(it).into(binding.uploadImageView)
            // not working
            val path =
                Uri.parse("android.resource://com.example.inviousgchallenge/" + binding.uploadImageView)
            imageUri = path
        }
        updateUI()
        initObservers()
        return binding.root
    }

    private fun updateUI() {
        binding.uploadImageView.setOnClickListener {
            selectImage()
        }
        binding.uploadButton.setOnClickListener {
            val description = binding.uploadDescription.text.toString()
            viewLifecycleOwner.lifecycleScope.launch {
                sessionViewModel.authState.value.user?.uid.let { user ->
                    uploadViewModel.addImageStorage(
                        imageUri,
                        description,
                        user!!
                    )
                }
                val action = UploadFragmentDirections.actionUploadFragmentToFeedFragment()
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            uploadViewModel.addImageStorageState.collect { state ->
                state.uriList?.firstOrNull()?.let {
                    uploadViewModel.imageUrlConsumed(it)
                    if (state.success!!) {
                        Toast.makeText(context, "Image Uploaded", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Error Uploading Image", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    imageUri = data.data!!
                }
                binding.uploadImageView.setImageURI(imageUri)
            }
        }
}