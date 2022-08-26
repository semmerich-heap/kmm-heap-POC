package com.heap.kmmpoc.shared.entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


//data class Entity()
@Serializable
data class Session(
    @SerialName("ID")
    val ID: String,
    @SerialName("Platform")
    val platform: String
)
@Serializable
data class Event (
    @SerialName("ID")
    val ID: String,
    @SerialName("EventText")
    val eventText: String
)