package com.example.testapplication.util

import com.example.testapplication.api.PlaceHolderComment
import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.api.PlaceHolderUser
import com.example.testapplication.dataBase.Comment
import com.example.testapplication.dataBase.Post
import com.example.testapplication.dataBase.User

fun PlaceHolderComment.toComment() =
        Comment (this.postId, this.id, this.name, this.email, this.body)

fun PlaceHolderPost.toPost() = Post(this.userId, this.id, this.title, this.body)

fun PlaceHolderUser.toUser() =
        User(this.id, this.name, this.username, this.email, this.address.toString())

object CustomKt {
    inline fun <T> forThem(vararg objs: T, block: T.() -> Unit) {
        for (obj in objs)
            obj.block()
    }
}
