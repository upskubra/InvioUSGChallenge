package com.example.inviousgchallenge.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.inviousgchallenge.R
import com.example.inviousgchallenge.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.feed -> {
                    navController.navigate(R.id.feedFragment)
                    true
                }
                R.id.catImage -> {
                    navController.navigate(R.id.apiImagesFragment)
                    true
                }
                R.id.trash -> {
                    navController.navigate(R.id.trashFragment)
                    true

                }
                else -> {
                    navController.navigate(R.id.feedFragment)
                    true
                }
            }
        }
    }
}