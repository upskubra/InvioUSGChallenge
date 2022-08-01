package com.example.inviousgchallenge.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.inviousgchallenge.adapter.FeedListAdapter
import com.example.inviousgchallenge.data.model.Image
import com.example.inviousgchallenge.databinding.FragmentFeedBinding
import com.example.inviousgchallenge.util.OnDoubleClickListenerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private lateinit var binding: FragmentFeedBinding
    private val feedViewModel: FeedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // set up the binding
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateFeedState()
        binding.addImageButton.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToUploadFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun updateFeedState() {
        viewLifecycleOwner.lifecycleScope.launch {
            feedViewModel.getFeedImages()
            feedViewModel.signIn()
            feedViewModel.authState.collect {
                if (it.signIn) {
                    updateImageList()

                } else {
                    Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateImageList() {
        viewLifecycleOwner.lifecycleScope.launch {
            feedViewModel.feedImageState.collect {
                if (it.isLoading) {
                    // binding.feedProgressBar.visibility = View.VISIBLE
                } else if (it.feedImageList?.isNotEmpty() == true && it.isLoading) {
                    // binding.catProgressBar.visibility = View.GONE
                    initRecycler(it.feedImageList!!)
                } else {
                    Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecycler(list: List<Image>) {
        binding.feedRecyclerView.apply {
            adapter = FeedListAdapter(list, object : OnDoubleClickListenerAdapter {
                override fun onClick(position: Int) {
                    //will review trash operation later
                    Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}