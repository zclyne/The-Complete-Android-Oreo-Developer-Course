package com.yifan.snapchatclone

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    private lateinit var auth: FirebaseAuth

    fun goClicked(view: View) {
        // check if we can login the user
        auth.signInWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    login()
                } else {
                    // If sign in fails, try to sign up the user and add the user to the database
                    auth.createUserWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                FirebaseDatabase.getInstance().reference
                                    .child("users")
                                    .child(task.result!!.user.uid)
                                    .child("email")
                                    .setValue(emailEditText?.text.toString())
                                login()
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(this, "Login Failed. Try Again.", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
    }

    fun login() {
        // move to next activity
        val intent = Intent(this, SnapsActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // check if signed in
        if (auth.currentUser != null) { // we have a current user
            login()
        }
    }
}
