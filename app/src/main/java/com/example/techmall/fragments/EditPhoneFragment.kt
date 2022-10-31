package com.example.techmall.fragments

import android.app.AlertDialog
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.techmall.R

class EditPhoneFragment : Fragment() {

    lateinit var lin_phone_color:LinearLayout
    lateinit var color_edit:TextView
    lateinit var phone_color:TextView
    lateinit var edit_color:EditText

    lateinit var lin_phone_screen:LinearLayout
    lateinit var display_edit:TextView
    lateinit var phone_display:TextView
    lateinit var edit_display:EditText

    lateinit var lin_phone_storage:LinearLayout
    lateinit var storage_edit:TextView
    lateinit var phone_storage:TextView
    lateinit var edit_storage:EditText

    lateinit var lin_phone_battery:LinearLayout
    lateinit var battery_edit:TextView
    lateinit var phone_battery:TextView
    lateinit var edit_battery:EditText

    lateinit var lin_phone_os:LinearLayout
    lateinit var os_edit:TextView
    lateinit var phone_os:TextView
    lateinit var edit_os:EditText

    lateinit var lin_phone_condition:LinearLayout
    lateinit var condition_edit:TextView
    lateinit var phone_condition:TextView
    lateinit var edit_condition:EditText

    lateinit var lin_phone_stock:LinearLayout
    lateinit var stock_edit:TextView
    lateinit var stocks_reduce:TextView
    lateinit var stock_count:TextView
    lateinit var stock_add:TextView

    lateinit var lin_phone_price:LinearLayout
    lateinit var price_edit:TextView
    lateinit var phone_price:TextView
    lateinit var edit_price:EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_edit_phone, container, false)

        lin_phone_color = view.findViewById(R.id.frag_phone_color_edit)
        color_edit = view.findViewById(R.id.color_phone_edit)
        phone_color = view.findViewById(R.id.phone_color_edit)
        edit_color = view.findViewById(R.id.edit_phone_color)
        lin_phone_color.setOnClickListener(View.OnClickListener {
            color_edit.visibility = View.GONE
            phone_color.visibility = View.GONE
            edit_color.visibility = View.VISIBLE
        })

        lin_phone_screen = view.findViewById(R.id.frag_phone_screen_sizes_edit)
        display_edit = view.findViewById(R.id.phone_display_edit)
        phone_display = view.findViewById(R.id.phone_screen_size_edit)
        edit_display = view.findViewById(R.id.phone_screen_edit)
        lin_phone_screen.setOnClickListener(View.OnClickListener {
            display_edit.visibility = View.GONE
            phone_display.visibility = View.GONE
            edit_display.visibility = View.VISIBLE
        })

        lin_phone_storage = view.findViewById(R.id.frag_phone_store_edit)
        storage_edit = view.findViewById(R.id.phone_storage_edit)
        phone_storage = view.findViewById(R.id.edit_phone_storage)
        edit_storage = view.findViewById(R.id.edit_phone_memory)
        lin_phone_storage.setOnClickListener(View.OnClickListener {
            edit_storage.visibility = View.GONE
            phone_storage.visibility = View.GONE
            edit_storage.visibility = View.VISIBLE
        })

        lin_phone_battery = view.findViewById(R.id.frag_phone_battery_edit)
        battery_edit = view.findViewById(R.id.phone_battery_edit)
        phone_battery = view.findViewById(R.id.phone_battery_capacity_edit)
        edit_battery = view.findViewById(R.id.edit_phone_battery)
        lin_phone_battery.setOnClickListener(View.OnClickListener {
            battery_edit.visibility = View.GONE
            phone_storage.visibility = View.GONE
            edit_storage.visibility = View.VISIBLE
        })

        lin_phone_os = view.findViewById(R.id.frag_phone_os_edit)
        os_edit = view.findViewById(R.id.phone_os_edit)
        phone_os = view.findViewById(R.id.phone_operating_system_edit)
        edit_os = view.findViewById(R.id.edit_phone_os)
        lin_phone_os.setOnClickListener(View.OnClickListener {
            os_edit.visibility = View.GONE
            phone_os.visibility = View.GONE
            edit_os.visibility = View.VISIBLE
        })

        lin_phone_condition = view.findViewById(R.id.frag_phone_conditions_edit)
        condition_edit = view.findViewById(R.id.phone_condition_edit)
        phone_condition = view.findViewById(R.id.edit_phone_condition)
        edit_condition = view.findViewById(R.id.change_phone_condition)
        lin_phone_os.setOnClickListener(View.OnClickListener {
            condition_edit.visibility = View.GONE
            phone_condition.visibility = View.GONE
            edit_condition.visibility = View.VISIBLE
        })

        lin_phone_stock = view.findViewById(R.id.lin_edit_phone_stock)
        stock_edit = view.findViewById(R.id.phone_stock)
        stock_add = view.findViewById(R.id.phone_stock_add)
        stock_count = view.findViewById(R.id.phone_stock_count)
        stocks_reduce = view.findViewById(R.id.phone_stock_reduce)

        var initial_count:String = stock_count.text.toString()
        var int_count:Int = initial_count.toInt()
        var new_count:Int = 0
        stock_add.setOnClickListener(View.OnClickListener {
            new_count = int_count + 1
            stock_count.text = new_count.toString()
        })

        stock_add.setOnClickListener(View.OnClickListener {
            if(int_count <= 0){

            } else {
                new_count = int_count + 1
                stock_count.text = new_count.toString()
            }
        })

        lin_phone_price = view.findViewById(R.id.lin_edit_phone_price)
        price_edit = view.findViewById(R.id.phone_price_edit)
        phone_price = view.findViewById(R.id.edit_phone_price)
        edit_price = view.findViewById(R.id.new_phone_price)
        lin_phone_price.setOnClickListener(View.OnClickListener {
            price_edit.visibility = View.GONE
            phone_price.visibility = View.GONE
            edit_price.visibility = View.VISIBLE
        })

        return view
    }


}