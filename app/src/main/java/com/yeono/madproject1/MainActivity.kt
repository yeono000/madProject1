package com.yeono.madproject1

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.yeono.madproject1.databinding.ActivityMainBinding
import com.yeono.madproject1.ui.game.PlayerDataModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_contact, R.id.navigation_dashboard, R.id.navigation_game
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val pl = PlayerDataModel("asd", "asdf", 2)
        Log.d("player", pl.toString())

        navView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_contact -> {
                    navController.popBackStack(
                        navController.graph.startDestinationId,
                        true
                    )
                    navController.navigate(R.id.navigation_contact)
                }
                R.id.navigation_dashboard -> {
                    navController.popBackStack(
                        navController.graph.startDestinationId,
                        true
                    )
                    navController.navigate(R.id.navigation_dashboard)

                }
                R.id.navigation_game -> {
                    navController.popBackStack(
                        navController.graph.startDestinationId,
                        true
                    )
                    navController.navigate(R.id.navigation_game)
                }
            }
            true
        }
    }
    fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, fragment)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}