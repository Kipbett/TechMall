package com.example.techmall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.example.techmall.R
import com.example.techmall.models.UserModel
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NewUserActivity : AppCompatActivity() {
    lateinit var user_profile:ImageView
    lateinit var user_email:EditText
    lateinit var user_phone:EditText
    lateinit var user_address:EditText
    lateinit var user_password:EditText
    lateinit var user_confirm_password:EditText
    lateinit var signup_button: Button
    lateinit var signin:TextView
    lateinit var user:UserModel

    var auth = FirebaseAuth.getInstance()
    var db = FirebaseDatabase.getInstance()
    var dbref:DatabaseReference = db.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        user_profile = findViewById(R.id.user_profile_image)
        user_email = findViewById(R.id.user_email_et)
        user_phone = findViewById(R.id.user_phone_et)
        user_address = findViewById(R.id.user_address_et)
        user_password = findViewById(R.id.user_password_et)
        user_confirm_password = findViewById(R.id.user_confirm_password_et)
        signup_button = findViewById(R.id.user_signup_btn)
        signin = findViewById(R.id.back_to_login)


        signin.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })

        signup_button.setOnClickListener(View.OnClickListener {

            var email = user_email.text.toString()
            var phone = user_phone.text.toString()
            var address = user_address.text.toString()
            var password = user_password.text.toString()
            var confirm_password = user_confirm_password.text.toString()

            if (confirm_password != password){
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
            } else if(TextUtils.isEmpty(email) || TextUtils.isEmpty(address) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)){
                Toast.makeText(this, "All Fields Must Be Filled", Toast.LENGTH_SHORT).show()
            }else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
                    task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                        user = UserModel(email, address, phone)
                        var user_id = auth.currentUser?.uid
                        dbref.child("users").child(user_id!!).setValue(user).addOnCompleteListener(this){
                            task ->
                            if (task.isSuccessful)
                                Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show()
                            else
                                Toast.makeText(this, "Data Saving Unsuccessful", Toast.LENGTH_SHORT).show()
                        }
                        var intent = Intent(this, SellerProductsActivity::class.java)
                        startActivity(intent)
                    } else
                        Toast.makeText(this, "Failed to create user", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}