package com.wolf.techmall.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.wolf.techmall.R

class CompEditFragment : Fragment() {

    //SCREEN VARIABLES
    lateinit var lin_screen_size:LinearLayout
    lateinit var display_edit:TextView
    lateinit var screen_size_edit:TextView
    lateinit var edit_display:EditText

    //STORAGE VARIABLES
    lateinit var comp_store_edit:LinearLayout
    lateinit var storage_edit:TextView
    lateinit var comp_storage_edit:TextView
    lateinit var comp_storage:TextView

    //PROCESSOR VARIABLES
    lateinit var lin_processor_edit:LinearLayout
    lateinit var processor_edit:TextView
    lateinit var speed_edit:TextView
    lateinit var edit_processor:EditText

    //OS VARIABLES
    lateinit var lin_os_edit:LinearLayout
    lateinit var os_edit:TextView
    lateinit var system_edit:TextView
    lateinit var edit_os:EditText

    //TOUCH SCREEN VARIABLES
    lateinit var lin_touch_edit:LinearLayout
    lateinit var comp_touch:TextView
    lateinit var screen_edit:TextView
    lateinit var edit_touch:EditText

    //PRICES VARIABLES
    lateinit var lin_price_edit:LinearLayout
    lateinit var price_edit:TextView
    lateinit var text_edit_price:TextView
    lateinit var edit_price:EditText

    //STOCK VARIABLES
    lateinit var stock_add:TextView
    lateinit var edit_total:TextView
    lateinit var stock_reduce:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_comp_edit, container, false)

        //SCREEN SIZE
        lin_screen_size = view.findViewById(R.id.frag_comp_screen_sizes_edit)
        display_edit = view.findViewById(R.id.comp_display_edit)
        screen_size_edit = view.findViewById(R.id.frag_comp_screen_size_edit)
        edit_display = view.findViewById(R.id.edit_comp_display)

        lin_screen_size.setOnClickListener(View.OnClickListener {
            display_edit.visibility = View.GONE
            screen_size_edit.visibility = View.GONE
            edit_display.visibility = View.VISIBLE
        })

        //EDIT STORAGE DETAILS
        comp_store_edit = view.findViewById(R.id.frag_comp_store_edit)
        storage_edit = view.findViewById(R.id.comp_storage_edit)
        comp_storage_edit = view.findViewById(R.id.frag_comp_storage_edit)
        comp_storage = view.findViewById(R.id.edit_comp_storage)

        comp_store_edit.setOnClickListener(View.OnClickListener {
            storage_edit.visibility = View.GONE
            comp_storage_edit.visibility = View.GONE
            comp_storage.visibility = View.VISIBLE
        })

        //EDIT PROCESSOR DETAILS
        lin_processor_edit = view.findViewById(R.id.frag_comp_processor_edit)
        processor_edit = view.findViewById(R.id.comp_processor_edit)
        speed_edit = view.findViewById(R.id.processor_speed_edit)
        edit_processor = view.findViewById(R.id.edit_comp_processor)

        lin_processor_edit.setOnClickListener(View.OnClickListener {
            processor_edit.visibility = View.GONE
            speed_edit.visibility = View.GONE
            edit_processor.visibility = View.VISIBLE
        })

        //EDIT OS DETAILS
        lin_os_edit = view.findViewById(R.id.frag_comp_os_edit)
        os_edit = view.findViewById(R.id.comp_os_edit)
        system_edit = view.findViewById(R.id.comp_operating_system_edit)
        edit_os = view.findViewById(R.id.edit_comp_os)

        lin_os_edit.setOnClickListener(View.OnClickListener {
            os_edit.visibility = View.GONE
            system_edit.visibility = View.GONE
            edit_os.visibility = View.VISIBLE
        })

        //EDIT TOUCH SCREEN DETAILS
        lin_touch_edit = view.findViewById(R.id.frag_comp_touch_edit)
        comp_touch = view.findViewById(R.id.edit_comp_touch)
        screen_edit = view.findViewById(R.id.comp_touch_screen_edit)
        edit_touch = view.findViewById(R.id.edit_comp_touch_screen)

        lin_touch_edit.setOnClickListener(View.OnClickListener {
            comp_touch.visibility = View.GONE
            screen_edit.visibility = View.GONE
            edit_touch.visibility = View.VISIBLE
        })

        //EDIT PRICE DETAILS
        lin_price_edit = view.findViewById(R.id.comp_price_edit)
        price_edit = view.findViewById(R.id.price_edit)
        text_edit_price = view.findViewById(R.id.text_edit_price)
        edit_price = view.findViewById(R.id.new_price)

        lin_price_edit.setOnClickListener(View.OnClickListener {
            price_edit.visibility = View.GONE
            text_edit_price.visibility = View.GONE
            edit_price.visibility = View.VISIBLE
        })

        //EDIT STOCK DETAILS
        stock_add = view.findViewById(R.id.stock_edit_add)
        edit_total = view.findViewById(R.id.stock_edit_total)
        stock_reduce = view.findViewById(R.id.stock_edit_minus)

        stock_add.setOnClickListener(View.OnClickListener {
            var initial_stock = edit_total.text.toString()
            var new_stock = initial_stock.toInt() + 1
            edit_total.text = new_stock.toString()
        })

        stock_reduce.setOnClickListener(View.OnClickListener {
            var initial_stock = edit_total.text.toString()
            var new_stock = initial_stock.toInt() - 1
            edit_total.text = new_stock.toString()
        })

        return view
    }

}