package com.heap.kmmpoc.shared.network


import com.heap.kmmpoc.shared.entity.HEvent
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class HeapAPI {
    companion object {
        private const val EVENTS_ENDPOINT = "https://events.free.beeceptor.com/events"
    }
        private val httpClient = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
            }

        }

    suspend fun sendAllEvents(events: List<HEvent>): HttpResponse {
        return httpClient.post(EVENTS_ENDPOINT) {
            contentType(ContentType.Application.Json)
            setBody(events)
        }
    }
    }