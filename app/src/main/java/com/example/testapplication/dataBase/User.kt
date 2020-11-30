package com.example.testapplication.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
class User(
        @field:ColumnInfo(name = "id") @field:PrimaryKey var id: Int,
        @field:ColumnInfo(name = "name") var name: String,
        @field:ColumnInfo(name = "username") var username: String,
        @field:ColumnInfo(name = "email") var email: String,
        @field:ColumnInfo(name = "address") var address: String,
)