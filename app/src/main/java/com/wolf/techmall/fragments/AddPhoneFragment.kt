package com.wolf.techmall.fragments

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.wolf.techmall.R
import com.wolf.techmall.activities.SellerProductsActivity
import com.wolf.techmall.models.AddPhoneModel
import com.wolf.techmall.models.PhoneDetails
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

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
    lateinit var phone_color:EditText
    lateinit var phone_additional:EditText

    var selected_image: Uri? = null
    lateinit var image_url:String

    var SELECT_PICTURE:Int = 200
    lateinit var content_resolver: ContentResolver

    var db_ref:DatabaseReference = FirebaseDatabase.getInstance().reference
    var auth = FirebaseAuth.getInstance()
    lateinit var add_phone_model:AddPhoneModel
    lateinit var phone_details:PhoneDetails


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
        phone_color = view.findViewById(R.id.new_phone_color_et)
        phone_additional = view.findViewById(R.id.new_phone_additional_text)

        phone_image.setOnClickListener(View.OnClickListener {
            var intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select Image"), SELECT_PICTURE)
        })

        btn_cancel_phone.setOnClickListener(View.OnClickListener {
            var intent = Intent(activity, SellerProductsActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        })

        btn_new_phone.setOnClickListener(View.OnClickListener {

            var brand:String = phone_brand.text.toString()
            var model:String = phone_model.text.toString()
            var os:String = phone_os.text.toString()
            var battery:String = phone_battery.text.toString()
            var memory:String = phone_memory.text.toString()
            var display:String = phone_display.text.toString()
            var condition:String = phone_condition.text.toString()
            var price:String = phone_price.text.toString()
            var stock:String = phone_stock.text.toString()
            var color:String = phone_color.text.toString()
            var c_info:String = phone_additional.text.toString()

            if (TextUtils.isEmpty(brand) || TextUtils.isEmpty(model) || TextUtils.isEmpty(os) || TextUtils.isEmpty(battery) ||
                TextUtils.isEmpty(memory) || TextUtils.isEmpty(display) || TextUtils.isEmpty(condition) ||
                TextUtils.isEmpty(price) || TextUtils.isEmpty(stock) || TextUtils.isEmpty(color)){
                Toast.makeText(activity, "Fields Cannot Be Empty \n Fill All The Spaces", Toast.LENGTH_LONG).show()
            } else {
                var pd = ProgressDialog(activity)
                pd.setMessage("Uploading")
                pd.show()

                if (selected_image != null){
                    var str_ref:StorageReference = FirebaseStorage.getInstance().reference
                        .child("Products").child("Smart Phones")
                        .child(auth.currentUser!!.uid).child("$brand $model.${getFileExtension(selected_image!!)}")

                    str_ref.putFile(selected_image!!).addOnCompleteListener(OnCompleteListener {
                        task ->
                        if (task.isComplete){
                            str_ref.downloadUrl.addOnCompleteListener {
                                task ->
                                if (task.isSuccessful){
                                    var img_uri:Uri = task.result
                                    image_url = img_uri.toString()
                                    add_phone_model = AddPhoneModel(brand, model, os, battery, memory, display, condition,
                                        price, stock, auth.currentUser!!.uid, image_url, "Smart Phones", color, c_info)
                                    phone_details = PhoneDetails("$brand $model", image_url, stock, price)
                                    db_ref.child("products").push().setValue(add_phone_model)
                                        .addOnCompleteListener(OnCompleteListener {
                                                task ->
                                            if (task.isSuccessful)
                                                Toast.makeText(activity, "$brand $model\n Added Successfully",
                                                    Toast.LENGTH_LONG).show()
                                            else
                                                Toast.makeText(activity, "$brand $model\n Not Successfully Added",
                                                    Toast.LENGTH_LONG).show()
                                        })

                                    db_ref.child("details").child(auth.currentUser!!.uid).push().setValue(phone_details)
                                        .addOnCompleteListener(OnCompleteListener {
                                                task ->
                                            if (task.isSuccessful)
                                                Toast.makeText(activity, "Added Successfully",
                                                    Toast.LENGTH_LONG).show()
                                            else
                                                Toast.makeText(activity, "Not Successfully Added",
                                                    Toast.LENGTH_LONG).show()
                                        })
                                    pd.dismiss()
                                    var intent = Intent(activity, SellerProductsActivity::class.java)
                                    startActivity(intent)
                                    requireActivity().finish()

                                    Toast.makeText(activity, "Image Upload Successful", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(activity, "Image Upload Unsuccessful", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    })
                } else
                    Toast.makeText(activity, "Image Upload Not Successful\n No Image Was Selected", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }

    private fun getFileExtension(uri: Uri): String? {
        var mime_type: MimeTypeMap = MimeTypeMap.getSingleton()
        content_resolver = requireActivity().contentResolver
        return mime_type.getExtensionFromMimeType(content_resolver.getType(uri))
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