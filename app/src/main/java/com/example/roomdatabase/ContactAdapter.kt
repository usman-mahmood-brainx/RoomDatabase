package com.example.roomdatabase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class ContactAdapter(private val context:Context, private val contactList: List<Contact>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_item_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val contact = contactList[position]

        holder.tvName.text = contact.name
        holder.tvPhone.text = contact.phone
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = formatter.format(contact.createdDate)
        holder.tvCreatedDate.text = formattedDate

        holder.btnEdit.setOnClickListener {
            val intent = Intent(context,CreateFormActivity::class.java)
            intent.putExtra("FormType","Update")
            intent.putExtra("id",contact.id.toInt())
            intent.putExtra("name",contact.name)
            intent.putExtra("phoneNumber",contact.phone)
            context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener {
            val database = ContactDatabase.getDatabase(context)
            GlobalScope.launch {
                database.contactDao().deleteContact(Contact(contact.id,contact.name,contact.phone,contact.createdDate))
            }

        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return contactList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ContactView: View) : RecyclerView.ViewHolder(ContactView) {
        val tvName: TextView = ContactView.findViewById(R.id.tvName)
        val tvPhone: TextView = ContactView.findViewById(R.id.tvPhone)
        val tvCreatedDate: TextView = ContactView.findViewById(R.id.tvCreatedDate)
        val btnEdit: Button = ContactView.findViewById(R.id.btnEdit)
        val btnDelete: Button = ContactView.findViewById(R.id.btnDelete)
    }
}