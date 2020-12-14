package com.example.sochial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    companion object {
        const val TAG = "LoginActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val auth=FirebaseAuth.getInstance()
        if(auth.currentUser!=null)
        {
            goPostsActivity()
        }


        btnLogin.setOnClickListener {
            btnLogin.isEnabled=false
            var email=etEmail.text.toString()
            var password=etPassword.text.toString()
            if(email.isEmpty() || password.isBlank()) {
                Toast.makeText(this, "Email or Password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//            Firebase authentication check
             val auth=FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task ->
                btnLogin.isEnabled=true
                if(task.isSuccessful){
                    Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                    goPostsActivity()
                }
                else{
                    Log.e(TAG,"Sign in with Email failed",task.exception)
                    Toast.makeText(this,"Authentication Failed",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun goPostsActivity() {
        Log.i(TAG, "goPostsActivity")
        val intent=Intent(this,PostsActivity::class.java)
        startActivity(intent)
        finish()
    }
}