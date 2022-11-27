package com.example.techmall.fragments

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.techmall.R
import com.example.techmall.activities.MainActivity
import com.google.android.material.button.MaterialButton

class SmartPhones : Fragment() {

    lateinit var phone_image:ImageView
    lateinit var phone_name:TextView
    lateinit var phone_supplier:TextView
    lateinit var phone_color:TextView
    lateinit var phone_display:TextView
    lateinit var phone_memory:TextView
    lateinit var phone_battery:TextView
    lateinit var phone_os:TextView
    lateinit var phone_camera:TextView
    lateinit var phone_condition:TextView
    lateinit var phone_call:MaterialButton
    lateinit var phone_message:MaterialButton

    private var REQUEST_CALL = 1
    private var SEND_MESSAGE = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_smart_phones, container, false)

        phone_image = view.findViewById(R.id.phone_img)
        phone_name = view.findViewById(R.id.phone_name)
        phone_supplier = view.findViewById(R.id.phone_supplier_name)
        phone_color = view.findViewById(R.id.phone_colour)
        phone_display = view.findViewById(R.id.phone_screen_size)
        phone_memory = view.findViewById(R.id.phone_storage)
        phone_battery = view.findViewById(R.id.phone_battery_capacity)
        phone_os = view.findViewById(R.id.phone_operating_system)
        phone_camera = view.findViewById(R.id.phone_camera)
        phone_condition = view.findViewById(R.id.phone_condition)
        phone_call  = view.findViewById(R.id.phone_call)
        phone_message = view.findViewById(R.id.phone_message)

        phone_call.setOnClickListener(View.OnClickListener {
            callUser()
        })

        phone_message.setOnClickListener(View.OnClickListener {
            messageUser()
        })
        return view
    }

    private fun callUser() {
        var intent = Intent(Intent.ACTION_CALL)
        intent.setData(Uri.parse("tel:+0700403306"))
        startActivity(intent)
    }

    private fun messageUser() {
        var intent = Intent(requireActivity().applicationContext, MainActivity::class.java)
        var pi = PendingIntent.getActivity(requireActivity().applicationContext, 0, intent, 0)
        var sms = SmsManager.getDefault()
        sms.sendTextMessage("0725585698", null, "Hell, I Want to Buy Your Product", pi, null)
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
                    callUser()
                } else {
                    Toast.makeText(activity, "Call Did not Go Through, Try again Later", Toast.LENGTH_SHORT)
                }
            }

            SEND_MESSAGE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    messageUser()
                    Toast.makeText(activity, "Message Sent", Toast.LENGTH_SHORT)
                } else {
                    Toast.makeText(activity, "Message Not Sent, Try again Later", Toast.LENGTH_SHORT)
                }
            }
        }
    }
}