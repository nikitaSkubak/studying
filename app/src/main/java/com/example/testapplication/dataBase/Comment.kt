package com.example.testapplication.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comment")
class Comment(
        @field:ColumnInfo(name = "postId") var postId: Int,
        @field:ColumnInfo(name = "id") @field:PrimaryKey var id: Int,
        @field:ColumnInfo(name = "name") var name: String,
        @field:ColumnInfo(name = "email") var email: String,
        @field:ColumnInfo(name = "body") var body: String
)