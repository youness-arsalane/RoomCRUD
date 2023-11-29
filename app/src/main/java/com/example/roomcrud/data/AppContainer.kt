package com.example.roomcrud.data

import android.content.Context
import com.example.roomcrud.data.repository.UsersRepository

interface AppContainer {
    val usersRepository: UsersRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val usersRepository = UsersRepository(
        RoomCRUDDatabase.DatabaseInstance.getInstance(context).userDao()
    )
}
