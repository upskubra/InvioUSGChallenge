package com.example.inviousgchallenge.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inviousgchallenge.adapter.ViewPagerAdapter
import com.example.inviousgchallenge.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val fragmentsArray = arrayOf(
        "Feed",
        "Trash",
        "Images"
    )
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupViewPager()
    }

    fun setupViewPager() {
        val viewPager = binding.pager
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = fragmentsArray[position]
        }.attach()
    }
}