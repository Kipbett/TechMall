package com.wolf.techmall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.wolf.techmall.R
import com.wolf.techmall.fragments.AddCompFragment
import com.wolf.techmall.fragments.AddPhoneFragment
import com.wolf.techmall.fragments.CompEditFragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

//        var category_comp = intent.getStringExtra("add_comp").toString()
//        var category_phone = intent.getStringExtra("add_phone").toString()



        val fragment_manager = supportFragmentManager
        val fragment_transaction = fragment_manager.beginTransaction()
        if (intent.getStringExtra("add_comp").toString().equals("Add Computer")){
            fragment_transaction.replace(R.id.frags_add, AddCompFragment()).commit()
        } else if (intent.getStringExtra("add_phone").toString().equals("Add Phone"))
            fragment_transaction.replace(R.id.frags_add, AddPhoneFragment()).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(applicationContext, SellerProductsActivity::class.java)
        startActivity(intent)
        finish()
    }
}