package dev.lennartegb.mongotodo.data

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

data class Todo(
    @PrimaryKey
    val id: ObjectId = ObjectId(),
    val value: String,
) : RealmObject
