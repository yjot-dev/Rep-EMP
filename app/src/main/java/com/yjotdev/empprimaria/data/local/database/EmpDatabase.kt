package com.yjotdev.empprimaria.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yjotdev.empprimaria.data.local.dao.UserDao
import com.yjotdev.empprimaria.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EmpDatabase: RoomDatabase() {
    companion object {
        const val NAME = "bd_emp"
    }

    abstract fun userDao(): UserDao
}