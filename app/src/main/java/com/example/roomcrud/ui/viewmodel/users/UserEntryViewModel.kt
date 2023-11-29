package com.example.roomcrud.ui.viewmodel.users


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.roomcrud.data.model.User
import com.example.roomcrud.data.repository.UsersRepository

class UserEntryViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {
    var userUiState by mutableStateOf(UserUiState())
        private set

    fun updateUiState(userDetails: UserDetails) {
        userUiState = UserUiState(
            userDetails = userDetails,
            isEntryValid = validateInput(userDetails)
        )
    }

    suspend fun saveUser() {
        if (!validateInput()) {
            return
        }

        val user = userUiState.userDetails.toUser()
        usersRepository.insertUser(user)
    }

    private fun validateInput(uiState: UserDetails = userUiState.userDetails): Boolean {
        return with(uiState) {
            email.isNotBlank() && firstName.isNotBlank() && lastName.isNotBlank()
        }
    }
}

data class UserUiState(
    val userDetails: UserDetails = UserDetails(),
    val isEntryValid: Boolean = false
)

data class UserDetails(
    val id: Int = 0,
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
)

fun UserDetails.toUser(): User = User(
    id = id,
    email = email,
    firstName = firstName,
    lastName = lastName
)

fun User.toUserUiState(isEntryValid: Boolean = false): UserUiState {
    return UserUiState(
        userDetails = this.toUserDetails(),
        isEntryValid = isEntryValid
    )
}

fun User.toUserDetails(): UserDetails {
    return UserDetails(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName
    )
}