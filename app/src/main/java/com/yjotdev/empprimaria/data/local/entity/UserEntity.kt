package com.yjotdev.empprimaria.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val photo: String = "",
    val isInvited: Boolean = false,
    val isInWhiteList: Boolean = false
)