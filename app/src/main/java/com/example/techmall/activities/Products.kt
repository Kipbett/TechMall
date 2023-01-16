package com.example.techmall.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techmall.R
import com.example.techmall.adapters.ProductAdapter
import com.example.techmall.models.PhoneDetails
import com.example.techmall.models.ProductModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.*

class Products : AppCompatActivity() {
    lateinit var recycler_view:RecyclerView
    lateinit var products_list:ArrayList<ProductModel>
    lateinit var products_adapter:ProductAdapter
    lateinit var search_view:SearchView
    lateinit var ad_view:AdView

    var db_ref = FirebaseDatabase.getInstance().reference
    lateinit var prod_details:PhoneDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        ad_view = findViewById(R.id.adViewHome)

        val ab = supportActionBar
        ab?.setDisplayShowHomeEnabled(true)
        ab?.setDisplayHomeAsUpEnabled(true)

        MobileAds.initialize(
            this
        ) { }

        val adRequest = AdRequest.Builder().build()
        ad_view.loadAd(adRequest)

        var category = intent.getStringExtra("category")
        supportActionBar?.title = category
        Toast.makeText(this, category, Toast.LENGTH_SHORT).show()

        recycler_view = findViewById(R.id.products_recycler)

        products_list = ArrayList()

        products_adapter = ProductAdapter(this, products_list)

        var layout_manager = GridLayoutManager(this, 3)
        recycler_view.layoutManager = layout_manager
        recycler_view.adapter = products_adapter

        db_ref.child("products").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                products_list.clear()
                for (data_snapshot in p0.children){
                    var p_category = data_snapshot.child("p_category").value.toString()
                    if (p_category.equals(category)){
                        val image = data_snapshot.child("p_imgurl").value.toString()
                        val name = "${data_snapshot.child("p_brand").value.toString()} ${data_snapshot.child("p_model").value.toString()}"
                        val price = "KSH. " + data_snapshot.child("p_price").value.toString()
                        products_list.add(ProductModel(image, name, price))
                    }
                }
                products_adapter.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.products_menu, menu)
        val search:MenuItem = menu!!.findItem(R.id.prod_search)
        search_view = MenuItemCompat.getActionView(search) as SearchView
        search_view.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                search_view.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                products_adapter.getFilter().filter(p0)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.prod_search -> Toast.makeText(this, "Search", Toast.LENGTH_LONG).show()
        }
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}