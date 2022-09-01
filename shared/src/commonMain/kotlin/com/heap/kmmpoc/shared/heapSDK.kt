package com.heap.kmmpoc.shared

import com.heap.kmmpoc.shared.cache.Database
import com.heap.kmmpoc.shared.cache.DatabaseDriverFactory
import com.heap.kmmpoc.shared.entity.HEvent
import com.heap.kmmpoc.shared.entity.HSession
import com.heap.kmmpoc.shared.network.HeapAPI


class HeapSDK (databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = HeapAPI()
    private val sessionId = 42.toString()

    @Throws(Exception::class) suspend fun initSDK(platform: String) {
        database.createSession(HSession(sessionId, platform  ))
    }
    @Throws(Exception::class) suspend fun getSessionId(): HSession {
      return database.getSession(sessionId)

    }
    @Throws(Exception::class) suspend fun getEvents(): List<HEvent> {
        return database.getAllEvents(sessionId)

    }
    @Throws(Exception::class) suspend fun sendEvent(id: String){

        //val session = database.getSession(sessionId)
     return database.createEvent(HEvent(id, "null"), "42")
    }

    @Throws(Exception::class) suspend fun sendEvents() {
        //val session = database.getSession(sessionId)
        database.getAllEvents(sessionId).also {
           api.sendAllEvents(it)
           database.clearEvents()
        }
    }
}
