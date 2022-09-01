package com.heap.kmmpoc.shared.entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


//data class Entity()
@Serializable
data class HSession(
    @SerialName("SessionID")
    val SID: String,
    @SerialName("Platform")
    val platform: String
)
@Serializable
class HEvent (
    @SerialName("EventID")
    val ID: String,
    @SerialName("EventText")
    val eventText: String
)