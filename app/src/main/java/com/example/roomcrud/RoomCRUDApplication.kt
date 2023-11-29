package com.example.roomcrud

import android.app.Application
import com.example.roomcrud.data.AppDataContainer

class RoomCRUDApplication : Application() {
    lateinit var container: AppDataContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
