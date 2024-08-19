package dev.lennartegb.data

data class Todo(
    val id: Id = Id(),
    val task: String,
) {
    @JvmInline
    value class Id internal constructor(val value: String = "")
}
