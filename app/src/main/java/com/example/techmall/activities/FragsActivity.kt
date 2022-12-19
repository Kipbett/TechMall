package com.example.techmall.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.techmall.R
import com.example.techmall.fragments.*

class FragsActivity : AppCompatActivity() {

    var bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frags)

        supportActionBar!!.title = intent.getStringExtra("p_category")
//        val img_p = intent.getStringExtra("img_url")
//        Toast.makeText(this, img_p, Toast.LENGTH_SHORT).show()
//        val cat = intent.getStringExtra("p_category")
        val fragment_manager = supportFragmentManager
        val fragment_transaction = fragment_manager.beginTransaction()

//        bundle.putString("image", img_p)
//        bundle.putString("cat", cat)
//        SmartPhones().arguments = bundle

//        fragment_transaction.replace(R.id.frags_layout, SmartPhones()).commit()

//        if(intent.getStringExtra("p_category").toString().equals("Computers")){
//            fragment_transaction.replace(R.id.frags_layout, Laptop()).commit()
//        }
//
//        if(intent.getStringExtra("p_category").toString().equals("Smart Phones")){
//            fragment_transaction.replace(R.id.frags_layout, SmartPhones()).commit()
//        }

    }
}