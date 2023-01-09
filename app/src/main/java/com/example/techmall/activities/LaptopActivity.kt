package com.example.techmall.activities

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.techmall.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class LaptopActivity : AppCompatActivity() {

    lateinit var comp_image: ImageView
    lateinit var comp_name: TextView
    lateinit var comp_supplier_name: TextView
    lateinit var comp_display: TextView
    lateinit var comp_storage: TextView
    lateinit var comp_processor: TextView
    lateinit var comp_os: TextView
    lateinit var comp_touch: TextView
    lateinit var comp_condition: TextView
    lateinit var call_user: MaterialButton
    lateinit var message_user: MaterialButton
    lateinit var comp_price: TextView

    private var REQUEST_CALL = 1
    private var SEND_MESSAGE = 0

    lateinit var email:String
    lateinit var number:String

    var dbref = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laptop)

        comp_name = findViewById(R.id.comp_prod_name)
        comp_supplier_name = findViewById(R.id.comp_supplier_name)
        comp_display = findViewById(R.id.comp_screen_size)
        comp_storage = findViewById(R.id.comp_storage)
        comp_processor = findViewById(R.id.comp_processor)
        comp_os = findViewById(R.id.comp_operating_system)
        comp_touch = findViewById(R.id.comp_touch_screen)
        comp_image = findViewById(R.id.comp_prod_img)
        comp_condition = findViewById(R.id.condition_view)
        call_user = findViewById(R.id.btn_comp_call)
        message_user = findViewById(R.id.btn_comp_message)
        comp_price = findViewById(R.id.price_view)

        var image = intent.getStringExtra("p_image")
        var category = intent.getStringExtra("p_category")

        dbref.child("products").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                for (ds in p0.children){
                    var p_image = ds.child("p_imgurl").value.toString()
                    if (image.equals(p_image)){
                        var p_name = "${ds.child("p_brand").value.toString() } ${ds.child("p_model").value.toString()}"
                        var p_display = ds.child("p_display").value.toString()
                        var p_storage = ds.child("p_memory").value.toString()
                        var p_processor = ds.child("p_processor").value.toString()
                        var p_os = ds.child("p_os").value.toString()
                        var p_touch = ds.child("p_touch").value.toString()
                        var p_condition = ds.child("p_condition").value.toString()
                        var p_user = ds.child("p_uid").value.toString()
                        var price = ds.child("p_price").value.toString()
                        dbref.child("users").addValueEventListener(object : ValueEventListener{
                            override fun onDataChange(p0: DataSnapshot) {
                                for (ds in p0.children){
                                    if (p_user.equals(ds.key.toString())){
                                        var u_name = ds.child("user_name").value.toString()
                                        var u_phone = ds.child("user_phone").value.toString()

                                        comp_supplier_name.text = u_name
                                        number = u_phone
                                    }
                                }
                            }

                            override fun onCancelled(p0: DatabaseError) {

                            }

                        })

                        Glide.with(applicationContext).load(p_image).into(comp_image)
                        comp_name.text = p_name
                        comp_condition.text = p_condition
                        comp_display.text = p_display
                        comp_os.text = p_os
                        comp_processor.text = p_processor
                        comp_touch.text = p_touch
                        comp_storage.text = p_storage
                        comp_price.text = "KES $price"

                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })

        call_user.setOnClickListener(View.OnClickListener {
            callUser(number)
        })

        message_user.setOnClickListener(View.OnClickListener {
            sendMessage(number)
        })
    }

    private fun sendMessage(phone:String) {
        var intent = Intent(applicationContext, MainActivity::class.java)
        var pi = PendingIntent.getActivity(applicationContext, 0, intent, 0)
        var sms = SmsManager.getDefault()
        sms.sendTextMessage(phone, null, "Hell, I Want to Buy Your Product", pi, null)
    }

    private fun callUser(phone:String) {
        var intent = Intent(Intent.ACTION_CALL)
        intent.setData(Uri.parse("tel:$phone"))
        startActivity(intent)
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
                    Toast.makeText(applicationContext, "Call Did not Go Through, Try again Later", Toast.LENGTH_SHORT)
                }
            }

            SEND_MESSAGE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    sendMessage(number)
                    Toast.makeText(applicationContext, "Message Sent", Toast.LENGTH_SHORT)
                } else {
                    Toast.makeText(applicationContext, "Message Not Sent, Try again Later", Toast.LENGTH_SHORT)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(applicationContext, Products::class.java)
        startActivity(intent)
        finish()
    }
}