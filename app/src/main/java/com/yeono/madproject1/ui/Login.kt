package com.yeono.madproject1.ui
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import android.widget.Toast
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//
//
///**
// * A simple [Fragment] subclass.
// * Use the [Login.newInstance] factory method to
// * create an instance of this fragment.
// */
//class Login : AppCompatActivity() {
//    // TODO: Rename and change types of parameters
//    private lateinit var auth: FirebaseAuth
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        auth = Firebase.auth
//
//        val btn = findViewById<Button>(R.id.noEmailLoginBtn)
//        btn.setOnClickListener {
//
//            auth.signInAnonymously()
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//
//
//                        val user = auth.currentUser
//
//                        Log.d("MainActivity", user!!.uid)
//
//                    } else {
//                        // If sign in fails, display a message to the user.
//
//                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
//
//                    }
//                }
//
//        }
//
//
//    }
//}