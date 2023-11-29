package com.example.roomcrud.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.roomcrud.RoomCRUDApplication
import com.example.roomcrud.ui.viewmodel.users.UserDetailsViewModel
import com.example.roomcrud.ui.viewmodel.users.UserEditViewModel
import com.example.roomcrud.ui.viewmodel.users.UserEntryViewModel
import com.example.roomcrud.ui.viewmodel.users.UserListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            UserEditViewModel(
                this.createSavedStateHandle(),
                roomCRUDApplication().container.usersRepository
            )
        }

        initializer {
            UserEntryViewModel(roomCRUDApplication().container.usersRepository)
        }

        initializer {
            UserDetailsViewModel(
                this.createSavedStateHandle(),
                roomCRUDApplication().container.usersRepository
            )
        }

        initializer {
            UserListViewModel(roomCRUDApplication().container.usersRepository)
        }
    }
}

fun CreationExtras.roomCRUDApplication(): RoomCRUDApplication {
    return (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RoomCRUDApplication)
}

