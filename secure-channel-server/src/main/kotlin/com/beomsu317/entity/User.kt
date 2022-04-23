package com.beomsu317.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class User(
    @BsonId
    val _id: Id<User> = newId(),
    val email: String,
    val passwordSha256WithSalt: String,
    val displayName: String,
    val photoUrl: String,
    val roomList: List<String>
)
