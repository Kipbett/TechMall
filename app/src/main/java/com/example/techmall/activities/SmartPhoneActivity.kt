package com.example.techmall.activities

import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.techmall.R
import com.example.techmall.models.SendCall
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SmartPhoneActivity : AppCompatActivity() {

    lateinit var phone_image: ImageView
    lateinit var phone_name: TextView
    lateinit var phone_supplier: TextView
    lateinit var phone_color: TextView
    lateinit var phone_display: TextView
    lateinit var phone_memory: TextView
    lateinit var phone_battery: TextView
    lateinit var phone_os: TextView
    lateinit var phone_camera: TextView
    lateinit var phone_condition: TextView
    lateinit var phone_call: MaterialButton
    lateinit var phone_message: MaterialButton
    lateinit var phone_price: TextView
    lateinit var phone_info:TextView
    lateinit var phone_add_info:TextView

    private var REQUEST_CALL = 0
    private var SEND_MESSAGE = 1

    lateinit var email:String
    lateinit var number:String

    var db_ref = FirebaseDatabase.getInstance().reference
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_phone)

        phone_image = findViewById(R.id.phone_img)
        phone_name = findViewById(R.id.phone_name)
        phone_supplier = findViewById(R.id.phone_supplier_name)
        phone_color = findViewById(R.id.phone_colour)
        phone_display = findViewById(R.id.phone_screen_size)
        phone_memory = findViewById(R.id.phone_storage)
        phone_battery = findViewById(R.id.phone_battery_capacity)
        phone_os = findViewById(R.id.phone_operating_system)
        phone_camera = findViewById(R.id.phone_camera)
        phone_condition = findViewById(R.id.phone_condition)
        phone_call = findViewById(R.id.phone_call)
        phone_message = findViewById(R.id.phone_message)
        phone_price = findViewById(R.id.phone_price)
        phone_info = findViewById(R.id.phone_info)
        phone_add_info = findViewById(R.id.phone_add_info)

        var image_url = intent.getStringExtra("p_image")
        var product_category = intent.getStringExtra("p_category")

        db_ref.child("products").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for(ds in p0.children){
                    var p_image = ds.child("p_imgurl").value.toString()
                    if (p_image.equals(image_url)){
                        var p_name = "${ds.child("p_brand").value.toString()} ${ds.child("p_model").value.toString()}"
                        var display = ds.child("p_display").value.toString()
                        var memory = ds.child("p_memory").value.toString()
                        var p_os = ds.child("p_os").value.toString()
                        var p_condition = ds.child("p_condition").value.toString()
                        var battery = ds.child("p_battery").value.toString()
                        var uid = ds.child("p_uid").value.toString()
                        var price = ds.child("p_price").value.toString()
                        val p_info = ds.child("p_additional").value.toString()
                        db_ref.child("users").addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(p0: DataSnapshot) {
                                for (data_s in p0.children){
                                    if (data_s.key.toString().equals(uid)){
                                        var p_number = data_s.child("user_phone").value.toString()
                                        var u_name = data_s.child("user_name").value.toString()

                                        phone_supplier.text = u_name
                                        number = p_number

                                    }
                                }
                            }

                            override fun onCancelled(p0: DatabaseError) {
                                Toast.makeText(applicationContext, "Operation Cancelled", Toast.LENGTH_SHORT).show()
                            }

                        })

                        Glide.with(applicationContext).load(p_image).into(phone_image)
                        phone_name.text = p_name
                        phone_display.text = display
                        phone_memory.text = memory
                        phone_battery.text = battery
                        phone_os.text = p_os
                        phone_condition.text = p_condition
                        phone_price.text = "KES $price"
                        if(p_info == null){
                            phone_add_info.visibility = View.GONE
                            phone_info.visibility = View.GONE
                        } else {
                            phone_info.text = p_info
                        }
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, "Error: $p0", Toast.LENGTH_SHORT).show()
            }

        })

        phone_call.setOnClickListener(View.OnClickListener {
            callUser(number)
        })

        phone_message.setOnClickListener(View.OnClickListener {
            messageUser(number)
        })
    }

    private fun callUser(p_number:String) {
//        checkForPermissions(android.Manifest.permission.CALL_PHONE, "Call", SEND_MESSAGE)
        var intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$p_number")
        startActivity(intent)
    }

    private fun messageUser(p_number:String) {
//        checkForPermissions(android.Manifest.permission.SEND_SMS, "Send Message", SEND_MESSAGE)
        var intent = Intent(applicationContext, MainActivity::class.java)
        var pi = PendingIntent.getActivity(applicationContext, 0, intent, 0)
        var sms = SmsManager.getDefault()
        sms.sendTextMessage(p_number, null, "Hell, I Want to Buy Your Product", pi, null)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CALL -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    callUser(number)
                } else {
                    Toast.makeText(this, "Call Did not Go Through, Try again Later", Toast.LENGTH_SHORT)
                }
            }

            SEND_MESSAGE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    messageUser(number)
                    Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT)
                } else {
                    Toast.makeText(this, "Message Not Sent, Try again Later", Toast.LENGTH_SHORT)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

//    private fun checkForPermissions(permission: String, name: String, request_code:Int){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            when{
//                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED -> {
//
//                }
//                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, request_code)
//
//                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), request_code)
//            }
//        }
//    }
//
//    private fun showDialog(permission: String, name: String, request_code: Int){
//        val builder = AlertDialog.Builder(this)
//        builder.apply {
//            setMessage("Permission to access $name is required")
//            setTitle("Permission Required")
//            setPositiveButton("Ok"){
//                    dialog, which ->
//                ActivityCompat.requestPermissions(this@SmartPhoneActivity, arrayOf(permission), request_code)
//            }
//            val dialog = builder.show()
//            dialog.show()
//        }
//    }
}