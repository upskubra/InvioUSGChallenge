package com.example.inviousgchallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.inviousgchallenge.Util.OnDoubleClickListenerAdapter
import com.example.inviousgchallenge.adapter.ApiImagesRecyclerAdapter
import com.example.inviousgchallenge.data.model.ApiImageItem
import com.example.inviousgchallenge.data.model.RequestState
import com.example.inviousgchallenge.databinding.FragmentApiImagesBinding
import com.example.inviousgchallenge.ui.ApiImagesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApiImagesFragment : Fragment() {
    private lateinit var binding: FragmentApiImagesBinding
    private val apiImagesViewModel: ApiImagesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApiImagesBinding.inflate(inflater)

        getDataFromApi()
        return binding.root
    }

    private fun getDataFromApi() {
        apiImagesViewModel.getApiImages()
        viewLifecycleOwner.lifecycleScope.launch {
            apiImagesViewModel.apiImageState.collect { requestState ->
                when (requestState) {
                    is RequestState.Success -> {
                        initRecycler(requestState.data)
                        binding.apiProgressBar.visibility = View.GONE
                    }
                    is RequestState.Loading -> {
                        binding.apiProgressBar.visibility = View.VISIBLE
                    }
                    else -> {
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun initRecycler(list: ArrayList<ApiImageItem>) {
        binding.apiRecyclerView.apply {
            adapter = ApiImagesRecyclerAdapter(list, object : OnDoubleClickListenerAdapter {
                override fun onClick(position: Int) {
                    //for add cloud gallery
                    Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}