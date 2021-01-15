package com.example.testapplication.util

import com.example.testapplication.api.PlaceHolderComment
import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.api.PlaceHolderUser
import com.example.testapplication.dataBase.Comment
import com.example.testapplication.dataBase.Post
import com.example.testapplication.dataBase.User

fun PlaceHolderComment.toComment() =
        Comment (postId, id, name, email, body)

fun PlaceHolderPost.toPost() = Post(userId, id, title, body)

fun PlaceHolderUser.toUser() =
        User(id, name, username, email, address.toString())

fun PlaceHolderUser.toUserTest() =
    User(id, name, username, email, "NY")

object CustomKt {
    inline fun <T> forThem(vararg objs: T, block: T.() -> Unit) {
        for (obj in objs)
            obj.block()
    }
}
