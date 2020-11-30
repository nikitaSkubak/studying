package com.example.testapplication.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Post")
class Post(
        @field:ColumnInfo(name = "userId") var userId: Int,
        @field:ColumnInfo(name = "id") @field:PrimaryKey var id: Int,
        @field:ColumnInfo(name = "title") var title: String,
        @field:ColumnInfo(name = "body") var body: String
)
