package com.example.roomcrud.ui.viewmodel.users

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomcrud.data.repository.UsersRepository
import com.example.roomcrud.ui.screens.users.UserDetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val userId: Int = checkNotNull(savedStateHandle[UserDetailsDestination.userIdArg])

    val uiState: StateFlow<UserDetailsUiState> =
        usersRepository.userDetails
            .map {
                if (it !== null) {
                    UserDetailsUiState(userDetails = it.toUserDetails())
                } else {
                    UserDetailsUiState()
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UserDetailsUiState()
            )

    init {
        viewModelScope.launch {
            usersRepository.getUser(userId)
        }
    }

    suspend fun deleteUser() {
        usersRepository.deleteUser(uiState.value.userDetails.toUser())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class UserDetailsUiState(
    var userDetails: UserDetails = UserDetails()
)