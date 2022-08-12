package com.example.inviousgchallenge.ui.upload

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
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
import java.io.File


@AndroidEntryPoint
class UploadFragment : Fragment() {
    private lateinit var binding: FragmentUploadBinding
    private val uploadViewModel: UploadViewModel by viewModels()
    private val sessionViewModel by activityViewModels<ActivityViewModel>()
    private var imageUri: Uri? = null
    private val args: UploadFragmentArgs by navArgs()
    private var msg: String? = ""
    private var lastMsg = ""

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUploadBinding.inflate(inflater)
        args.catUrl?.let { url ->
            Glide.with(this).load(url).into(binding.uploadImageView)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                requestPermissions()
            } else {
                args.catName?.let { name ->
                    downloadImage(url, name)
                }
            }
        }
        clickListener()
        initObservers()
        return binding.root
    }

    private fun clickListener() {
        binding.uploadImageView.setOnClickListener {
            selectImage()
        }
        binding.uploadButton.setOnClickListener {
            val description = binding.uploadDescription.text.toString()
            viewLifecycleOwner.lifecycleScope.launch {
                sessionViewModel.authState.value.user?.uid.let { user ->
                    try {
                        uploadViewModel.addImageStorage(
                            imageUri!!,
                            description,
                            user!!
                        )
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                    }
                    if (uploadViewModel.addImageStorageState.value.success == true) {
                        val action = UploadFragmentDirections.actionUploadFragmentToFeedFragment()
                        Navigation.findNavController(it).navigate(action)
                    }
                }
            }
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            uploadViewModel.addImageStorageState.collect { state ->
                if (state.success!!) {
                    Toast.makeText(context, "Image Uploaded", Toast.LENGTH_LONG).show()
                } else if (state.description == null || state.uri == null) {
                    Toast.makeText(context, "Please complete everything", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Error Uploading Image", Toast.LENGTH_LONG)
                        .show()
                }
                state.uriList?.firstOrNull()?.let {
                    uploadViewModel.imageUrlConsumed(it)
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

    @SuppressLint("Range")
    private fun downloadImage(url: String, name: String) {
        val directory = File(Environment.DIRECTORY_DOWNLOADS)

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val downloadManager =
            requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadUri = Uri.parse(url)

        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(true)
                .setTitle(name)
                .setDescription("")
                .setDestinationInExternalPublicDir(
                    directory.toString(), name
                )
        }

        val downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)
        Thread(Runnable {
            var downloading = true
            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                msg = statusMessage(status)
                if (msg != lastMsg) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    }
                    // get uri
                    downloadManager.getUriForDownloadedFile(downloadId)?.let {
                        imageUri = it
                    }
                    lastMsg = msg ?: ""
                }
                cursor.close()
            }
        }).start()
    }

    private fun statusMessage(status: Int): String {
        var msg = ""
        msg = when (status) {
            DownloadManager.STATUS_FAILED -> "Download has been failed, please try again"
            DownloadManager.STATUS_PAUSED -> "Paused"
            DownloadManager.STATUS_PENDING -> "Pending"
            DownloadManager.STATUS_RUNNING -> "Downloading..."
            DownloadManager.STATUS_SUCCESSFUL -> "Image downloaded successfully"
            else -> "There's nothing to download"
        }
        return msg
    }

    private fun requestPermissions() {
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.values.all { it }) {
                downloadImage(args.catUrl!!, args.catName!!)
            } else {
                Toast.makeText(requireContext(), "Permissions not granted", Toast.LENGTH_SHORT)
                    .show()
            }
        }.launch(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
    }
}