package com.example.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var tvGetData:TextView
    lateinit var btnInsert:Button
    lateinit var database: ContactDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        database = ContactDatabase.getDatabase(this)
        val database2 = ContactDatabase.getDatabase(this)
        GlobalScope.launch {
            Log.d("Thread Name","${Thread.currentThread().name}")
            database.contactDao().insertContact(Contact(0,"john cena","99939", Date()))
        }
        Log.d("Thread Name","${Thread.currentThread().name}")

//        database.contactDao().getContact().observe(this, Observer {
//            println(it.toString())
//        })
        
        tvGetData = findViewById(R.id.getData)
        tvGetData.setOnClickListener {
            database.contactDao().getContact().observe(this, Observer {
               println(it.toString())
            })
        }

        btnInsert = findViewById(R.id.insert)
        btnInsert.setOnClickListener {
            GlobalScope.launch {
                database.contactDao().insertContact(contact = Contact(0,"usman","3434",Date()))
                Log.d("Thread Name","${Thread.currentThread().name}")

            }
        }




    }


}