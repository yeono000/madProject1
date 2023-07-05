package com.yeono.madproject1

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yeono.madproject1.databinding.ActivityMainBinding
import com.yeono.madproject1.user.UserDto
import com.yeono.madproject1.user.UserModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val receivedValue = intent.getStringExtra("uid")
        val userViewModel: UserModel by viewModels()
        if (receivedValue != null){
            userViewModel.setUserId(receivedValue)
            Log.d("user", "receive"+userViewModel.uid)
        }
        db = Firebase.firestore
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                Log.d("db", "uid"+userViewModel.uid.toString())
                var i = 0
                for (document in result) {
                    val objects = document.toObject(UserDto::class.java)
                    if(objects.uid == userViewModel.uid){
                        Log.d("db", objects.uid.toString()?:"")
                        val objects = document.toObject(UserDto::class.java)
                        userViewModel.setUserName(objects.name?:"")
                        userViewModel.addUser(objects)
                        userViewModel.addPlayer(i)
                    }
                    else{userViewModel.addUser(objects)}
                    i += 1
                }
                Log.d("db", userViewModel.userList.value!!.size.toString())
                Log.d("db", userViewModel.friendList.value!!.size.toString())
                Log.d("db", userViewModel.playerList.value!!.size.toString())
            }
            .addOnFailureListener { exception ->
                Log.w("db", "Error getting documents.", exception)
            }
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