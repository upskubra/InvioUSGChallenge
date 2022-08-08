package com.example.inviousgchallenge.ui.cat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.inviousgchallenge.R
import com.example.inviousgchallenge.adapter.CatListAdapter
import com.example.inviousgchallenge.data.model.CatImageItem
import com.example.inviousgchallenge.databinding.FragmentCatBinding
import com.example.inviousgchallenge.util.OnDoubleClickListenerAdapter
import com.example.inviousgchallenge.util.loadUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatFragment : Fragment() {
    private lateinit var binding: FragmentCatBinding
    private val catViewModel: CatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatBinding.inflate(inflater)
        updateCatState()
        return binding.root
    }

    private fun updateCatState() {
        viewLifecycleOwner.lifecycleScope.launch {
            catViewModel.getCatImages()
            catViewModel.catImageState.collect {
                if (it.isLoading) {
                    binding.catProgressBar.visibility = View.VISIBLE
                } else if (it.catList.isNotEmpty()) {
                    binding.catProgressBar.visibility = View.GONE
                    initRecycler(it.catList)
                } else {
                    Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecycler(list: List<CatImageItem>) {
        binding.catRecyclerView.apply {
            adapter = CatListAdapter(list, object : OnDoubleClickListenerAdapter {
                override fun onClick(position: Int) {
                    //for add cloud gallery
                    val url = list[position].image?.url
                  val action = CatFragmentDirections.actionApiImagesFragmentToUploadFragment(url)
                    Navigation.findNavController(binding.root).navigate(action)
                    Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
