package com.example.techmall.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.example.techmall.R
import com.google.android.material.button.MaterialButton

class AddPhoneFragment : Fragment() {

    lateinit var phone_image:ImageView
    lateinit var phone_brand:EditText
    lateinit var phone_model:EditText
    lateinit var phone_os:EditText
    lateinit var phone_battery:EditText
    lateinit var phone_memory:EditText
    lateinit var phone_display:EditText
    lateinit var phone_condition:EditText
    lateinit var phone_price:EditText
    lateinit var phone_stock:EditText
    lateinit var btn_new_phone:MaterialButton
    lateinit var btn_cancel_phone:MaterialButton

    var selected_image: Uri? = null

    var SELECT_PICTURE:Int = 200

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_add_phone, container, false)

        phone_brand = view.findViewById(R.id.new_phone_brand_et)
        phone_model = view.findViewById(R.id.new_phone_model_et)
        phone_os = view.findViewById(R.id.new_phone_os_et)
        phone_battery = view.findViewById(R.id.new_phone_battery_et)
        phone_memory = view.findViewById(R.id.new_phone_memory_et)
        phone_display = view.findViewById(R.id.new_phone_screen_et)
        phone_condition = view.findViewById(R.id.new_phone_condition_et)
        phone_price = view.findViewById(R.id.new_phone_price_et)
        phone_stock = view.findViewById(R.id.new_phone_stock_et)
        btn_new_phone = view.findViewById(R.id.add_phone)
        btn_cancel_phone = view.findViewById(R.id.cancel_add_phone)
        phone_image = view.findViewById(R.id.new_phone_image)

        var brand:String = phone_brand.text.toString()
        var model:String = phone_model.text.toString()
        var os:String = phone_os.text.toString()
        var battery:String = phone_battery.text.toString()
        var memory:String = phone_memory.text.toString()
        var display:String = phone_display.text.toString()
        var condition:String = phone_condition.text.toString()
        var price:String = phone_price.text.toString()
        var stock:String = phone_stock.text.toString()

        phone_image.setOnClickListener(View.OnClickListener {
            var intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select Image"), SELECT_PICTURE)
        })

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK){
            if (requestCode == SELECT_PICTURE){
                selected_image = data!!.data
                if(null != selected_image){
                    phone_image.setImageURI(selected_image)
                }
            }
        }

    }

}