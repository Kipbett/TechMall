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
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.techmall.R
import com.example.techmall.activities.MainActivity
import com.google.android.material.button.MaterialButton
import com.google.api.Distribution

class Laptop : Fragment() {

    lateinit var comp_image:ImageView
    lateinit var comp_name:TextView
    lateinit var comp_supplier_name:TextView
    lateinit var comp_display:TextView
    lateinit var comp_storage:TextView
    lateinit var comp_processor:TextView
    lateinit var comp_os:TextView
    lateinit var comp_touch:TextView
    lateinit var comp_condition:TextView
    lateinit var call_user:MaterialButton
    lateinit var message_user:MaterialButton

    lateinit var supplier_lin:LinearLayout
    lateinit var comp_supplier:TextView
    lateinit var description:TextView

    lateinit var lin_desc:LinearLayout
    lateinit var comp_disp:TextView
    lateinit var lin_storage:LinearLayout
    lateinit var comp_store:TextView
    lateinit var lin_processor:LinearLayout
    lateinit var comp_proc:TextView
    lateinit var lin_os:LinearLayout
    lateinit var comp_osys:TextView
    lateinit var lin_touch:LinearLayout
    lateinit var comp_tch:TextView
    lateinit var lin_condition:LinearLayout
    lateinit var comp_con:TextView
    lateinit var lin_button:LinearLayout

    private var REQUEST_CALL = 1
    private var SEND_MESSAGE = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_laptop, container, false)
        comp_image = view.findViewById(R.id.comp_prod_img)
        comp_name = view.findViewById(R.id.comp_prod_name)
        comp_supplier_name = view.findViewById(R.id.comp_supplier_name)
        comp_display = view.findViewById(R.id.comp_screen_size)
        comp_storage = view.findViewById(R.id.comp_storage)
        comp_processor = view.findViewById(R.id.comp_processor)
        comp_os = view.findViewById(R.id.comp_operating_system)
        comp_touch = view.findViewById(R.id.comp_touch_screen)
        comp_image = view.findViewById(R.id.comp_prod_img)
        comp_condition = view.findViewById(R.id.condition_view)
        call_user = view.findViewById(R.id.btn_comp_call)
        message_user = view.findViewById(R.id.btn_comp_message)

        supplier_lin = view.findViewById(R.id.lin_comp_supplier)
        comp_supplier = view.findViewById(R.id.comp_supplier)
        description = view.findViewById(R.id.comp_description)
        lin_desc = view.findViewById(R.id.lin_comp_screen_size)
        comp_disp = view.findViewById(R.id.comp_display)
        lin_storage = view.findViewById(R.id.lin_comp_storage)
        comp_store = view.findViewById(R.id.comp_store)
        lin_processor = view.findViewById(R.id.lin_comp_processor)
        comp_proc = view.findViewById(R.id.comp_proc)
        lin_os = view.findViewById(R.id.lin_comp_os)
        comp_osys = view.findViewById(R.id.comp_os)
        lin_touch = view.findViewById(R.id.lin_comp_touch)
        comp_tch = view.findViewById(R.id.comp_tch)
        lin_condition = view.findViewById(R.id.lin_comp_condition)
        comp_con = view.findViewById(R.id.comp_con)
        lin_button = view.findViewById(R.id.lin_comp_button)

        call_user.setOnClickListener(View.OnClickListener {
            callUser()
        })

        message_user.setOnClickListener(View.OnClickListener {
            sendMessage()
        })
        return view
    }

    private fun sendMessage() {
        var intent = Intent(requireActivity().applicationContext, MainActivity::class.java)
        var pi = PendingIntent.getActivity(requireActivity().applicationContext, 0, intent, 0)
        var sms = SmsManager.getDefault()
        sms.sendTextMessage("0725585698", null, "Hell, I Want to Buy Your Product", pi, null)
    }

    private fun callUser() {
        var intent = Intent(Intent.ACTION_CALL)
        intent.setData(Uri.parse("tel:+0700403306"))
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
                    callUser()
                } else {
                    Toast.makeText(activity, "Call Did not Go Through, Try again Later", Toast.LENGTH_SHORT)
                }
            }

            SEND_MESSAGE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    sendMessage()
                    Toast.makeText(activity, "Message Sent", Toast.LENGTH_SHORT)
                } else {
                    Toast.makeText(activity, "Message Not Sent, Try again Later", Toast.LENGTH_SHORT)
                }
            }
        }
    }
}