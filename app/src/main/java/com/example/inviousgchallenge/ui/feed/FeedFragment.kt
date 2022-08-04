package com.example.inviousgchallenge.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.inviousgchallenge.adapter.FeedListAdapter
import com.example.inviousgchallenge.data.model.Image
import com.example.inviousgchallenge.databinding.FragmentFeedBinding
import com.example.inviousgchallenge.ui.activity.ActivityViewModel
import com.example.inviousgchallenge.util.OnDoubleClickListenerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private lateinit var binding: FragmentFeedBinding
    private val feedViewModel: FeedViewModel by viewModels()
    private val sessionViewModel by activityViewModels<ActivityViewModel>()

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
        observeUser()
    }

    private fun updateFeedState(user: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            feedViewModel.getFeedImages(user)
        }
        binding.addImageButton.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToUploadFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun observeUser() {
        viewLifecycleOwner.lifecycleScope.launch {
            sessionViewModel.signIn()
            sessionViewModel.authState.collect {
                if (it.user == null) {
                    Toast.makeText(context, "Please login to continue", Toast.LENGTH_LONG).show()
                    binding.feedRecyclerView.visibility = View.GONE
                    binding.feedProgressBar.visibility = View.GONE
                    binding.addImageButton.visibility = View.GONE
                } else {
                    val user = it.user!!.uid
                    updateFeedState(user)
                    binding.addImageButton.visibility = View.VISIBLE
                    observeFeedImages()
                }
            }
        }
    }

    private fun observeFeedImages() {
        viewLifecycleOwner.lifecycleScope.launch {
            feedViewModel.feedImageState.collect {
                if (it.loading) {
                    binding.feedProgressBar.visibility = View.VISIBLE
                } else if (it.imageList != null) {
                    binding.feedProgressBar.visibility = View.GONE
                    initRecycler(it.imageList!!)
                } else {
                    binding.feedRecyclerView.visibility = View.GONE
                    Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecycler(list: List<Image>) {
        binding.feedRecyclerView.apply {
            adapter = FeedListAdapter(list, object : OnDoubleClickListenerAdapter {
                override fun onClick(position: Int) {
                    Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}