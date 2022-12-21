package com.example.techmall.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.techmall.R
import com.example.techmall.adapters.CategoryAdapter
import com.example.techmall.adapters.OffersAdapter
import com.example.techmall.adapters.ProductAdapter
import com.example.techmall.adapters.SuggestedAdapter
import com.example.techmall.models.CategoreisModel
import com.example.techmall.models.ProductModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var category_txt:TextView
    lateinit var recyclerViewCategory: RecyclerView
    lateinit var offer_txt:TextView
    lateinit var recyclerViewOffer:RecyclerView
    lateinit var suggested_txt:TextView
    lateinit var recyclerViewSuggested:RecyclerView
    lateinit var toolbar:androidx.appcompat.widget.Toolbar
    lateinit var nav_view:NavigationView
    lateinit var drawer_layout:DrawerLayout
    lateinit var usr_email:TextView
    lateinit var usr_image:ImageView
    lateinit var usr_name:TextView


    lateinit var categories:ArrayList<CategoreisModel>
    lateinit var products:ArrayList<ProductModel>

    var longitude = 0.0
    var latitude = 0.0

    lateinit var productAdapter: ProductAdapter
    lateinit var category_adapter: CategoryAdapter
    lateinit var offer_adapter:OffersAdapter
    lateinit var suggested_adapter:SuggestedAdapter

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    var db_ref = FirebaseDatabase.getInstance().reference
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var action_bar = supportActionBar

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){

            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            }
        } else {
            getCurrentLocation()
        }

        toolbar = findViewById(R.id.tool_bar)
        setSupportActionBar(toolbar)
        drawer_layout = findViewById(R.id.drawer_layout)
        nav_view = findViewById(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener(this)

        var drawer_toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(drawer_toggle)
        drawer_toggle.syncState()

        category_txt = findViewById(R.id.categories)
        offer_txt = findViewById(R.id.offers_tv)
        suggested_txt = findViewById(R.id.suggested_tv)
        recyclerViewSuggested = findViewById(R.id.suggested_recyclerview)
        recyclerViewCategory = findViewById(R.id.categories_recycler)
        recyclerViewOffer = findViewById(R.id.offers_recycler)
        usr_email = findViewById(R.id.user_email)
        usr_image = findViewById(R.id.avator)
        usr_name = findViewById(R.id.user_name)


        categories = ArrayList()
        categories.add(CategoreisModel(R.drawable.computer, "Computers"))
        categories.add(CategoreisModel(R.drawable.film, "Cameras"))
        categories.add(CategoreisModel(R.drawable.truck, "Home Appliances"))
        categories.add(CategoreisModel(R.drawable.computer, "Comp Accessories"))
        categories.add(CategoreisModel(R.drawable.film, "Phones & Tablets"))
        categories.add(CategoreisModel(R.drawable.computer, "Home Theatre"))

        products = ArrayList()

        category_adapter = CategoryAdapter(this, categories)
        offer_adapter = OffersAdapter(this, categories)
        suggested_adapter = SuggestedAdapter(this, categories)
        productAdapter = ProductAdapter(this, products)

        var layout_manager_category = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.layoutManager = layout_manager_category
        recyclerViewCategory.adapter = category_adapter

        var layout_manager_offers = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewOffer.layoutManager = layout_manager_offers
        recyclerViewOffer.adapter = offer_adapter

        var layout_manager_suggested = GridLayoutManager(this, 2)
        recyclerViewSuggested.layoutManager = layout_manager_suggested
        recyclerViewSuggested.adapter = productAdapter

        db_ref.child("products").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                products.clear()
                for (ds in dataSnapshot.children){
                    var p_image = ds.child("p_imgurl").value.toString()
                    var p_name = ds.child("p_brand").value.toString() + " " + ds.child("p_model").value.toString()
                    var p_price = ds.child("p_price").value.toString()
                    products.add(ProductModel(p_image, p_name, p_price))
                }
                productAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(db_error: DatabaseError) {
                Toast.makeText(applicationContext, "Error: $db_error", Toast.LENGTH_SHORT).show()
            }

        })

        db_ref.child("users").child(auth.currentUser!!.uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                for(ds in p0.children){
                    var u_image = ds.child("user_image").value.toString()
                    var user_name = ds.child("user_name").value.toString()
                    var user_email = ds.child("user+email").value.toString()

                    usr_email.text = user_email
                    usr_name.text = user_name
                    Glide.with(applicationContext).load(u_image).into(usr_image)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastKnownLocation != null) {
            latitude = lastKnownLocation.latitude
            longitude = lastKnownLocation.longitude
            // Do something with the location
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    getCurrentLocation()
                } else {

                }
                return
            }

            else -> {

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.search_menu -> Toast.makeText(this, "Search Item", Toast.LENGTH_LONG).show()
            R.id.cart_menu -> Toast.makeText(this, "Add Item To Cart", Toast.LENGTH_LONG).show()
            R.id.login_menu -> Toast.makeText(this, "Login", Toast.LENGTH_LONG).show()
            R.id.menu_seller -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.computers ->{
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, Products::class.java)
                intent.putExtra("category", "Computers")
                startActivity(intent)
                finish()
            }
            R.id.gaming_consoles -> {
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, Products::class.java)
                intent.putExtra("category", "Game Consoles")
                startActivity(intent)
                finish()
            }
            R.id.smart_phones -> {
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, Products::class.java)
                intent.putExtra("category", "Smart Phones")
                startActivity(intent)
                finish()
            }
            R.id.home_appliances -> {
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, Products::class.java)
                intent.putExtra("category", "Home Appliances")
                startActivity(intent)
                finish()
            }
            R.id.home_theaters -> {
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, Products::class.java)
                intent.putExtra("category", "Home Theaters")
                startActivity(intent)
                finish()
            }
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
            }
            else -> print("No Selection Made")
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {

        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.isDrawerOpen(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}

