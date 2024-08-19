package dev.lennartegb.mongotodo.data

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Todo : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var value: String = ""
}
