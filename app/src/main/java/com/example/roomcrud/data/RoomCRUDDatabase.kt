package com.example.roomcrud.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomcrud.data.dao.UserDao
import com.example.roomcrud.data.model.User

@Database(
    entities = [
        User::class
    ],
    version = 1
)
abstract class RoomCRUDDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    object DatabaseInstance {
        @Volatile
        private var instance: RoomCRUDDatabase? = null

        fun getInstance(context: Context): RoomCRUDDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomCRUDDatabase::class.java,
                        "room_crud"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }

                return instance!!
            }
        }
    }
}
