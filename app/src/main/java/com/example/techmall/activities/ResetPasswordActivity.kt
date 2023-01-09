package com.example.techmall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.techmall.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ResetPasswordActivity : AppCompatActivity() {

    lateinit var reset_btn:Button
    lateinit var reset_email:EditText
    var auth = FirebaseAuth.getInstance()
    var db_ref = FirebaseDatabase.getInstance().reference
    lateinit var usr_email:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        reset_email = findViewById(R.id.user_email_reset_et)
        reset_btn = findViewById(R.id.user_reset_btn)

        reset_btn.setOnClickListener(View.OnClickListener {
            usr_email = reset_email.text.toString()
            if (TextUtils.isEmpty(usr_email)){
                Toast.makeText(this, "Email Field Cannot Be Empty", Toast.LENGTH_LONG).show()
            }
            else {
                resetPassword(usr_email)
            }

        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun resetPassword(email: String) {
        val auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Password reset email sent
                    Toast.makeText(applicationContext, "Password Reset Sent to Your Email", Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    // Error occurred
                    Toast.makeText(applicationContext, "Error Occurred, Try Again", Toast.LENGTH_LONG).show()
                }
            }
    }

}