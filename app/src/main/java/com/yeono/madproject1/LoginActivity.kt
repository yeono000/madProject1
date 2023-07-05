package com.yeono.madproject1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yeono.madproject1.user.UserDto

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        auth = Firebase.auth
//        auth.signOut()
        db = Firebase.firestore
        val currentUser = auth.currentUser
        val nameText = findViewById<EditText>(R.id.user_name_area)
        if (currentUser == null){
            val loginButton = findViewById<Button>(R.id.login_button)
            loginButton.setOnClickListener{
                auth.signInAnonymously()
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            Toast.makeText(
                                baseContext,
                                "Authentication success.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            var userDto = UserDto()
                            userDto.uid = auth?.currentUser?.uid
                            userDto.name = nameText.text.toString()
                            db.collection("users")
                                .add(userDto)
                                .addOnSuccessListener { documentReference ->
                                    Log.d("db", "DocumentSnapshot added with ID: ${nameText}")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("db", "Error adding document", e)
                                }
                            updateUI(user, nameText.text.toString())
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            updateUI(null, null)
                        }
                    }
            }
        }
        else{
            updateUI(currentUser, nameText.text.toString())
        }
    }
    fun updateUI(user : FirebaseUser?, nameText: String?){
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("uid", user?.uid)
            intent.putExtra("name", nameText)
            startActivity(intent)
            finish()
        }, 0)
    }
}