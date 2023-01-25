package com.wolf.techmall.activities

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
import com.wolf.techmall.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
    lateinit var comp_info:TextView
    lateinit var comp_add_info:TextView

    lateinit var p_name:String

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
        comp_info = findViewById(R.id.comp_info)
        comp_add_info = findViewById(R.id.comp_add_info)

        var image = intent.getStringExtra("p_image")
        var category = intent.getStringExtra("p_category")

        dbref.child("products").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                for (ds in p0.children){
                    var p_image = ds.child("p_imgurl").value.toString()
                    if (image.equals(p_image)){
                        p_name = "${ds.child("p_brand").value.toString() } ${ds.child("p_model").value.toString()}"
                        val p_display = ds.child("p_display").value.toString()
                        val p_storage = ds.child("p_memory").value.toString()
                        val p_processor = ds.child("p_processor").value.toString()
                        val p_os = ds.child("p_os").value.toString()
                        val p_touch = ds.child("p_touch").value.toString()
                        val p_condition = ds.child("p_condition").value.toString()
                        val p_user = ds.child("p_uid").value.toString()
                        val price = ds.child("p_price").value.toString()
                        val p_info = ds.child("p_additional").value.toString()
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
                        if (p_info.equals("null")){
                            comp_info.visibility = View.GONE
                            comp_add_info.visibility = View.GONE
                        } else {
                            comp_info.text = p_info
                        }

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
//        checkForPermissions(android.Manifest.permission.SEND_SMS, "Send Message", SEND_MESSAGE)
        var intent = Intent(applicationContext, MainActivity::class.java)
        var pi = PendingIntent.getActivity(applicationContext, 0, intent, 0)
        var sms = SmsManager.getDefault()
        sms.sendTextMessage(phone, null, "Hello, I Want to Buy Your Product $p_name.\nLet's Get In Touch", pi, null)
    }

    private fun callUser(phone:String) {
//        checkForPermissions(android.Manifest.permission.CALL_PHONE, "Call", REQUEST_CALL)
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
//                dialog, which ->
//                ActivityCompat.requestPermissions(this@LaptopActivity, arrayOf(permission), request_code)
//            }
//            val dialog = builder.show()
//            dialog.show()
//        }
//    }
}