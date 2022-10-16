package com.example.techmall.activities

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.techmall.R

class ProductReviewActivity : AppCompatActivity() {

    lateinit var text_discount:TextView
    lateinit var text_seller:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_review)

        text_discount = findViewById(R.id.disc_price)
        text_seller = findViewById(R.id.prod_seller)

        text_discount.paintFlags = text_discount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        var action_bar = supportActionBar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.products_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.prod_search -> Toast.makeText(this, "Search", Toast.LENGTH_LONG).show()
            R.id.prod_cart -> Toast.makeText(this, "Cart", Toast.LENGTH_LONG).show()
            R.id.prod_profile -> Toast.makeText(this, "Profile", Toast.LENGTH_LONG).show()
        }
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}