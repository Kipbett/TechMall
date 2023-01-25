package com.wolf.techmall.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.wolf.techmall.R
import com.wolf.techmall.adapters.ProductAdapter
import com.wolf.techmall.models.ProductModel

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

    private val REQUEST_CALL = 1
    private val REQUEST_SMS = 0

//    var interstitialAd: InterstitialAd? = null
    lateinit var adRequest: AdRequest

    var db_ref = FirebaseDatabase.getInstance().reference
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkForPermissions(android.Manifest.permission.CALL_PHONE, "Access Phone", REQUEST_CALL)
        checkForPermissions(android.Manifest.permission.SEND_SMS, "Access Messages", REQUEST_SMS)

        MobileAds.initialize(this)
        adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, "ca-app-pub-3683437462836746/9525322022",
            adRequest, object:InterstitialAdLoadCallback(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Toast.makeText(applicationContext, "Ad Failed To Load $p0", Toast.LENGTH_LONG).show()
                }

                override fun onAdLoaded(p0: InterstitialAd) {
                    super.onAdLoaded(p0)
                    p0.show(MainActivity())
                    Toast.makeText(applicationContext, "Ad Loaded", Toast.LENGTH_LONG).show()

                    p0.fullScreenContentCallback = object : FullScreenContentCallback(){
                        override fun onAdClicked() {
                            super.onAdClicked()
                            Toast.makeText(applicationContext, "Ad Clicked", Toast.LENGTH_LONG).show()
                        }

                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            Toast.makeText(applicationContext, "Ad Dismissed", Toast.LENGTH_LONG).show()
                        }

                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            super.onAdFailedToShowFullScreenContent(p0)
                            Toast.makeText(applicationContext, "Ad Failed To show \n$p0", Toast.LENGTH_LONG).show()
                        }

                        override fun onAdImpression() {
                            super.onAdImpression()
                            Toast.makeText(applicationContext, "Ad Impression Count", Toast.LENGTH_LONG).show()
                        }

                        override fun onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent()
                            p0.show(MainActivity())
                        }
                    }
                }
            }
        )

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


        var layout_manager_suggested = GridLayoutManager(this, 3)
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
                    products.add(ProductModel(p_image, p_name, p_price))
                }
                productAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(db_error: DatabaseError) {
                Toast.makeText(applicationContext, "Error: $db_error", Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CALL){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(applicationContext, "Call Permission Granted", Toast.LENGTH_SHORT)
                } else {
                    checkForPermissions(android.Manifest.permission.CALL_PHONE, "Access Phone", REQUEST_CALL)
                    Toast.makeText(applicationContext, "Call Did not Go Through, Try again Later", Toast.LENGTH_SHORT).show()
                }
        }

        if (requestCode == REQUEST_SMS){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(applicationContext, "Messages Permission Granted", Toast.LENGTH_SHORT)
                } else {
                    checkForPermissions(android.Manifest.permission.CALL_PHONE, "Access Phone", REQUEST_SMS)
                    Toast.makeText(applicationContext, "Message Not Sent, Try again Later", Toast.LENGTH_SHORT)
                }
        }

    }

    private fun checkForPermissions(permission: String, name: String, request_code:Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED -> {}
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, request_code)
                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), request_code)
            }
        }
    }

    private fun showDialog(permission: String, name: String, request_code: Int){
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("Permission to access $name is required")
            setTitle("Permission Required")
            setPositiveButton("Ok"){
                    dialog, which ->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), request_code)
            }
            val dialog = builder.show()
            dialog.show()
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

            R.id.home_user -> {
                if (auth.currentUser?.uid != null){
                    val intent = Intent(applicationContext, UserProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
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
//            R.id.gaming_consoles -> {
//                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
//                var intent = Intent(this, Products::class.java)
//                intent.putExtra("category", "Game Consoles")
//                startActivity(intent)
//                finish()
//            }
            R.id.smart_phones -> {
                var intent = Intent(this, Products::class.java)
                intent.putExtra("category", "Smart Phones")
                startActivity(intent)
                finish()
            }
//            R.id.home_appliances -> {
//                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
//                var intent = Intent(this, Products::class.java)
//                intent.putExtra("category", "Home Appliances")
//                startActivity(intent)
//                finish()
//            }
//            R.id.home_theaters -> {
//                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
//                var intent = Intent(this, Products::class.java)
//                intent.putExtra("category", "Home Theaters")
//                startActivity(intent)
//                finish()
//            }

            R.id.user_sell -> {
                if(auth.currentUser?.uid != null){
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(applicationContext, SellerProductsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.logout -> {
                if(auth.currentUser?.uid == null){
                    Toast.makeText(applicationContext, "User Not Logged In", Toast.LENGTH_LONG).show()
                } else {
                    item.title = "Logout"
                }

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

    override fun onStart() {
        super.onStart()
        checkForPermissions(android.Manifest.permission.CALL_PHONE, "Access Phone", REQUEST_CALL)
        checkForPermissions(android.Manifest.permission.SEND_SMS, "Access Messages", REQUEST_SMS)

    }
}

