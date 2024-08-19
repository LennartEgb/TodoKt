package dev.lennartegb.data.impl

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

internal class RealmTodo : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var value: String = ""
}