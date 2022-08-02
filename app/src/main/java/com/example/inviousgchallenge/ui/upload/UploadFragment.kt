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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.inviousgchallenge.databinding.FragmentUploadBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UploadFragment : Fragment() {
    private lateinit var binding: FragmentUploadBinding
    private val uploadViewModel: UploadViewModel by viewModels()
    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUploadBinding.inflate(inflater)
        updateUI()
        initObservers()
        return binding.root
    }

    private fun updateUI() {
        binding.uploadImageView.setOnClickListener {
            selectImage()
        }
        binding.uploadButton.setOnClickListener {
            uploadViewModel.addImageStorage(imageUri)
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