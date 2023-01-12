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
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
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
    lateinit var suggested_txt:TextView
    lateinit var recyclerViewSuggested:RecyclerView
    lateinit var toolbar:androidx.appcompat.widget.Toolbar
    lateinit var nav_view:NavigationView
    lateinit var drawer_layout:DrawerLayout
    var name_head:TextView? = null
    var email_head:TextView? = null
    lateinit var search_view:SearchView

    lateinit var products:ArrayList<ProductModel>

    var longitude = 0.0
    var latitude = 0.0

    lateinit var productAdapter: ProductAdapter

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    var db_ref = FirebaseDatabase.getInstance().reference
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var action_bar = supportActionBar

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){

            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)
            }
        } else {
            getCurrentLocation()
        }

        name_head = findViewById(R.id.user_name_head)
        email_head = findViewById(R.id.user_email_head)

        var uid = auth.currentUser?.uid

        if (auth.currentUser?.uid != null){
            db_ref.child("user").addValueEventListener(object: ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    for (ds in p0.children){
                        var user_id = ds.key
                        if(user_id.equals(uid)){
                            val user_name = ds.child("user_name").value.toString()
                            val user_email = ds.child("user_email").value.toString()

                            name_head!!.text = user_name
                            email_head!!.text = user_email
                        }
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                }

            })
        } else {
            Toast.makeText(this, "User is null", Toast.LENGTH_SHORT).show()
            name_head?.text = "Null"
            email_head?.text = "Null"
        }

        toolbar = findViewById(R.id.tool_bar)
        setSupportActionBar(toolbar)
        drawer_layout = findViewById(R.id.drawer_layout)
        nav_view = findViewById(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener(this)
        var drawer_toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(drawer_toggle)
        drawer_toggle.syncState()
        suggested_txt = findViewById(R.id.suggested_tv)
        recyclerViewSuggested = findViewById(R.id.suggested_recyclerview)
        products = ArrayList()
        productAdapter = ProductAdapter(this, products)


        var layout_manager_suggested = GridLayoutManager(this, 2)
        recyclerViewSuggested.layoutManager = layout_manager_suggested
        recyclerViewSuggested.adapter = productAdapter

        db_ref.child("products").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                products.clear()
                for (ds in dataSnapshot.children){
                    var p_image = ds.child("p_imgurl").value.toString()
                    var p_name = ds.child("p_brand").value.toString() + " " + ds.child("p_model").value.toString()
                    var p_price = "KES ${ds.child("p_price").value.toString()}"
                    var prod_category = ds.child("p_category").value.toString()
                    products.add(ProductModel(p_image, p_name, p_price, prod_category))
                }
                productAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(db_error: DatabaseError) {
                Toast.makeText(applicationContext, "Error: $db_error", Toast.LENGTH_SHORT).show()
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
                }
                return
            }

            else -> {

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        val search:MenuItem = menu!!.findItem(R.id.search_menu)
        search_view = MenuItemCompat.getActionView(search) as SearchView
        search_view.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                search_view.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                productAdapter.getFilter().filter(p0)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.search_menu -> {
                Toast.makeText(this, "Search Item", Toast.LENGTH_LONG).show()
            }
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
        finish()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}

