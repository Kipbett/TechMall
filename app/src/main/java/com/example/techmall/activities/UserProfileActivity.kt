package com.example.techmall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.techmall.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class UserProfileActivity : AppCompatActivity() {

    lateinit var user_image:ImageView
    lateinit var user_name:TextView
    lateinit var user_email:TextView
    lateinit var user_phone:TextView
    lateinit var user_address:TextView

    var auth = FirebaseAuth.getInstance()
    var db_ref = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        user_image = findViewById(R.id.profile_image)
        user_name = findViewById(R.id.user_profile_name)
        user_email = findViewById(R.id.user_profile_email)
        user_phone = findViewById(R.id.user_profile_phone)
        user_address = findViewById(R.id.user_profile_address)

        val ab = supportActionBar
        ab?.setDisplayShowHomeEnabled(true)
        ab?.setDisplayHomeAsUpEnabled(true)


        if (auth.currentUser?.uid != null){

            db_ref.child("users").addValueEventListener(object:
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds:DataSnapshot in snapshot.children){
                        val ui = ds.key
                        if (ui.equals(auth.currentUser!!.uid.toString())){
                            val img_url = ds.child("user_image").value.toString()
                            val u_name = ds.child("user_name").value.toString()
                            val u_email = ds.child("user_email").value.toString()
                            val u_phone = ds.child("user_phone").value.toString()
                            val u_address = ds.child("user_address").value.toString()

                            Glide.with(applicationContext).load(img_url).into(user_image)
                            user_name.text = u_name
                            user_email.text = u_email
                            user_phone.text = u_phone
                            user_address.text = u_address
                            ab?.title = u_name

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "Error: $error", Toast.LENGTH_LONG).show()
                }

            })

        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout_profile -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.delete_profile -> {
                Toast.makeText(applicationContext, "Cant Delete Profile At the Moment", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}