package com.example.roomdatabase

import android.content.Context
import androidx.room.*

@Database(entities = [Contact::class], version = 1)
@TypeConverters(Convertors::class)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDAO

    // Thread Safe Approach
    companion object{
        // Whenever our instance value assigned its updated value will be available to all threads
        @Volatile
        private var INSTANCE:ContactDatabase? = null

        fun getDatabase(context:Context):ContactDatabase{
            if(INSTANCE == null){
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, ContactDatabase::class.java, "contactDB"
                    ).build()
                }

            }
            return INSTANCE!!
        }
    }
}
