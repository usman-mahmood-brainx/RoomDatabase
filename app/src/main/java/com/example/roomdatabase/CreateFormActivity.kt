package com.example.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.example.roomdatabase.databinding.ActivityCreateFormBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class CreateFormActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val formType =  intent.getStringExtra("FormType",)
        val id = intent.getIntExtra("id",0)
        
        if(formType == "Add")
            binding.tvFormLabel.text = "Add Contact"
        else{
            binding.tvFormLabel.text = "Update Contact"
            val name = intent.getStringExtra("name")
            binding.etName.setText(name)
            binding.etPhoneNumber.setText(intent.getStringExtra("phoneNumber"))
            binding.btnSave.text = "Update"
        }

        binding.btnSave.setOnClickListener {

            val database = ContactDatabase.getDatabase(this)
            val name  = binding.etName.text.toString().trim()
            val phone  = binding.etPhoneNumber.text.toString().trim()
            val contact = Contact( id.toLong(),name,phone, Date())

            if(formType == "Add"){
                GlobalScope.launch {
                    database.contactDao().insertContact(contact)
                }
            }
            else{
                GlobalScope.launch {
                    database.contactDao().updateContact(contact)
                }
            }

            finish()

        }

    }

  
}