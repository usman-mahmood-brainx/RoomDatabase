package com.example.roomdatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var database: ContactDatabase
    lateinit var contactList: MutableList<Contact>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = ContactDatabase.getDatabase(this)
        contactList = mutableListOf()

        val contactAdapter = ContactAdapter(this,contactList)
        binding.rvContacts.layoutManager = LinearLayoutManager(this)
        binding.rvContacts.adapter = contactAdapter


        database.contactDao().getContact().observe(this, Observer { liveContactsList->
           contactList.clear()
            contactList.addAll( liveContactsList.reversed())
            contactAdapter.notifyDataSetChanged()

        })





        binding.btnAdd.setOnClickListener {
            val intent = Intent(this@MainActivity,CreateFormActivity::class.java)
            intent.putExtra("FormType","Add" )
            startActivity(intent)
        }

//        Log.d("Thread Name","${Thread.currentThread().name}")





    }


}