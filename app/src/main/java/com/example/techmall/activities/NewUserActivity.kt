package com.example.techmall.activities

import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import com.example.techmall.R
import com.example.techmall.models.UserModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class NewUserActivity : AppCompatActivity() {
    lateinit var user_profile:ImageView
    lateinit var user_email:EditText
    lateinit var user_phone:EditText
    lateinit var user_address:EditText
    lateinit var user_password:EditText
    lateinit var user_confirm_password:EditText
    lateinit var signup_button: Button
    lateinit var signin:TextView
    lateinit var user:UserModel

    lateinit var content_resolver:ContentResolver

    var auth = FirebaseAuth.getInstance()
    var db = FirebaseDatabase.getInstance()
    var dbref:DatabaseReference = db.reference

    var SELECTED_PROFILE = 200
    var select_profile:Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        user_profile = findViewById(R.id.user_profile_image)
        user_email = findViewById(R.id.user_email_et)
        user_phone = findViewById(R.id.user_phone_et)
        user_address = findViewById(R.id.user_address_et)
        user_password = findViewById(R.id.user_password_et)
        user_confirm_password = findViewById(R.id.user_confirm_password_et)
        signup_button = findViewById(R.id.user_signup_btn)
        signin = findViewById(R.id.back_to_login)

        user_profile.setOnClickListener(View.OnClickListener {
            var intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Choose Profile Image"), SELECTED_PROFILE)
        })


        signin.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })

        signup_button.setOnClickListener(View.OnClickListener {

            var email = user_email.text.toString()
            var phone = user_phone.text.toString()
            var address = user_address.text.toString()
            var password = user_password.text.toString()
            var confirm_password = user_confirm_password.text.toString()

            if (confirm_password != password){
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
            } else if(TextUtils.isEmpty(email) || TextUtils.isEmpty(address) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)){
                Toast.makeText(this, "All Fields Must Be Filled", Toast.LENGTH_SHORT).show()
            }else {

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
                    task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                        user = UserModel(email, address, phone)
                        var user_id = auth.currentUser?.uid
                        dbref.child("users").child(user_id!!).setValue(user).addOnCompleteListener(this){
                            task ->
                            if (task.isSuccessful){
                                Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show()
                            } else
                                Toast.makeText(this, "Data Saving Unsuccessful", Toast.LENGTH_SHORT).show()
                        }
                        var intent = Intent(this, SellerProductsActivity::class.java)
                        startActivity(intent)
                    } else
                        Toast.makeText(this, "Failed to create user", Toast.LENGTH_SHORT).show()
                }

                var pd = ProgressDialog(this)
                pd.setMessage("Saving Data")
                pd.show()
                if (select_profile != null){
                    var str_ref:StorageReference = FirebaseStorage.getInstance().reference
                        .child("User Profile").child("$email.${getFileExtension(select_profile!!)}")
                    str_ref.putFile(select_profile!!).addOnCompleteListener(OnCompleteListener {
                            task ->
                        if (task.isComplete){
                            str_ref.downloadUrl.addOnCompleteListener {
                                    task ->
                                if (task.isSuccessful){
                                    pd.dismiss()
                                    Toast.makeText(this, "Profile saved successfully", Toast.LENGTH_SHORT)
                                        .show()
                                } else
                                    Toast.makeText(this, "Profile Image not Saved", Toast.LENGTH_SHORT)
                                        .show()
                            }
                        }
                    })
                } else
                    pd.dismiss()
                    Toast.makeText(this, "Profile Image not Saved\n File Path is Missing", Toast.LENGTH_SHORT)
                        .show()
            }
        })
    }

    private fun getFileExtension(uri: Uri):String?{
        content_resolver = contentResolver
        var mime_type:MimeTypeMap = MimeTypeMap.getSingleton()
        return mime_type.getMimeTypeFromExtension(content_resolver.getType(uri))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == SELECTED_PROFILE){
            select_profile = data!!.data
            Toast.makeText(this, "$select_profile", Toast.LENGTH_SHORT).show()
            if (select_profile != null)
                user_profile.setImageURI(select_profile)
        }
    }
}