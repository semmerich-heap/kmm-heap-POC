package com.heap.kmmpoc.shared.network


import com.heap.kmmpoc.shared.entity.Event
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class HeapAPI {
    companion object {
        private const val EVENTS_ENDPOINT = "https://api.spacexdata.com/v3/launches"
    }
        private val httpClient = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
            }
        }

    suspend fun sendAllEvents(events: List<Event>): HttpResponse {
        return httpClient.post(EVENTS_ENDPOINT) {
            setBody(events)
        }
    }
    }