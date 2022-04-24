package com.beomsu317.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class Room(
    @BsonId val _id: Id<Room> = newId(),
    val title: String,
    val users: List<String>
)
