package com.example.roomcrud.data.repository

import com.example.roomcrud.data.dao.UserDao
import com.example.roomcrud.data.model.User
import kotlinx.coroutines.flow.Flow

class UsersRepository(private val userDao: UserDao) {
    fun getAllUsersStream(): Flow<List<User>> = userDao.getAllUsers()

    fun getUserStream(id: Int): Flow<User?> = userDao.getUser(id)

    fun getUserByEmail(email: String): Flow<User?> = userDao.getUserByEmail(email)

    suspend fun insertUser(user: User) = userDao.insert(user)

    suspend fun updateUser(user: User) = userDao.update(user)

    suspend fun deleteUser(user: User) = userDao.delete(user)
}
