package com.example.techmall.fragments

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.media.Image
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
import androidx.recyclerview.widget.RecyclerView
import com.example.techmall.R
import com.example.techmall.activities.AddItemActivity
import com.example.techmall.activities.SellerProductsActivity
import com.example.techmall.models.AddProductModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.internal.EdgeToEdgeUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.opencensus.internal.StringUtils

class AddCompFragment : Fragment() {

    lateinit var comp_image:ImageView
    lateinit var comp_brand:EditText
    lateinit var comp_model:EditText
    lateinit var comp_os:EditText
    lateinit var comp_proecessor:EditText
    lateinit var comp_memory:EditText
    lateinit var comp_display:EditText
    lateinit var comp_touch:EditText
    lateinit var comp_condition:EditText
    lateinit var comp_stock:EditText
    lateinit var comp_price:EditText
    lateinit var add_btn:MaterialButton
    lateinit var cancel_btn:MaterialButton

    lateinit var content_resolver:ContentResolver

    var auth = FirebaseAuth.getInstance()
    var db_ref:DatabaseReference = FirebaseDatabase.getInstance().reference


    private var SELECT_PIC = 200
    private var image_uri:Uri? = null

    lateinit var products_model:AddProductModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_add_comp, container, false)

        comp_image= view.findViewById(R.id.new_comp_image)
        comp_brand= view.findViewById(R.id.new_comp_brand_et)
        comp_model= view.findViewById(R.id.new_comp_model_et)
        comp_os= view.findViewById(R.id.new_comp_os_et)
        comp_proecessor= view.findViewById(R.id.new_comp_processor_et)
        comp_memory= view.findViewById(R.id.new_comp_memory_et)
        comp_display= view.findViewById(R.id.new_comp_screen_et)
        comp_touch= view.findViewById(R.id.new_comp_touch_et)
        comp_condition= view.findViewById(R.id.new_comp_condition_et)
        comp_price = view.findViewById(R.id.new_comp_price_et)
        comp_stock = view.findViewById(R.id.new_comp_stock_et)
        add_btn = view.findViewById(R.id.add_comp)
        cancel_btn = view.findViewById(R.id.cancel_add_comp)

        comp_image.setOnClickListener(View.OnClickListener {
            var intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select Comp Image"), SELECT_PIC)
        })


        add_btn.setOnClickListener(View.OnClickListener {


            var p_brand = comp_brand.text.toString()
            var p_model = comp_model.text.toString()
            var p_os = comp_os.text.toString()
            var p_processor = comp_proecessor.text.toString()
            var p_memory = comp_memory.text.toString()
            var p_display = comp_display.text.toString()
            var p_touch = comp_touch.text.toString()
            var p_condition = comp_condition.text.toString()
            var p_price = comp_price.text.toString()
            var p_stock = comp_stock.text.toString()
            if (TextUtils.isEmpty(p_brand) || TextUtils.isEmpty(p_model) || TextUtils.isEmpty(p_os) ||
                TextUtils.isEmpty(p_processor) || TextUtils.isEmpty(p_memory) ||
                TextUtils.isEmpty(p_display) || TextUtils.isEmpty(p_touch) || TextUtils.isEmpty(p_condition) ||
                TextUtils.isEmpty(p_price) || TextUtils.isEmpty(p_stock)){
                Toast.makeText(activity, "Fields Cannot Be Empty \n Fill All The Spaces", Toast.LENGTH_LONG).show()
            } else {

                var pd = ProgressDialog(activity)
                pd.setMessage("Uploading")
                pd.show()

               if(image_uri != null){
                   var str_ref:StorageReference = FirebaseStorage.getInstance().reference.child("Products").child("Computers").child(auth.currentUser!!.uid)
                       .child("$p_brand $p_model.${getFileExtension(image_uri!!)}")
                   str_ref.putFile(image_uri!!).addOnCompleteListener(OnCompleteListener {
                       task ->
                       if (task.isComplete){
                           str_ref.downloadUrl.addOnCompleteListener {
                               task ->
                               if (task.isSuccessful){
                                   pd.dismiss()
                                   Toast.makeText(activity, "Image Upload Successful", Toast.LENGTH_SHORT).show()
                                   var intent = Intent(activity, SellerProductsActivity::class.java)
                                   startActivity(intent)
                               } else {
                                   Toast.makeText(activity, "Image Upload Unsuccessful", Toast.LENGTH_SHORT).show()
                               }
                           }
                       }
                   })

               } else {
                   Toast.makeText(activity, "Image Upload Not Successful\n No Image Was Selected", Toast.LENGTH_SHORT).show()
               }
                products_model = AddProductModel(p_brand, p_model, p_os, p_processor, p_memory, p_display, p_touch,
                    p_condition, p_price, p_stock)
                db_ref.child("products").child("Computers").push().setValue(products_model)
                    .addOnCompleteListener(
                    OnCompleteListener { task ->
                        if (task.isSuccessful){
                            Toast.makeText(activity, "$p_brand $p_model\n Added Successfully",
                                Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(activity, "$p_brand $p_model\n Not Successfully Added",
                                Toast.LENGTH_LONG).show()
                        }
                    })
            }

        })

        cancel_btn.setOnClickListener(View.OnClickListener {
            var intent = Intent(activity, SellerProductsActivity::class.java)
            startActivity(intent)
        })
        return view
    }

    private fun getFileExtension(uri: Uri): String? {
        var mime_type:MimeTypeMap = MimeTypeMap.getSingleton()
        content_resolver = requireActivity().contentResolver
        return mime_type.getExtensionFromMimeType(content_resolver.getType(uri))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == SELECT_PIC){
            image_uri = data!!.data
            if (image_uri != null){
                comp_image.setImageURI(image_uri)
            }
        }
    }

}