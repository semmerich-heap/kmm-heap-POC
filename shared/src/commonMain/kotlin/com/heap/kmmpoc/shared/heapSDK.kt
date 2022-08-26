package com.heap.kmmpoc.shared

import com.heap.kmmpoc.shared.cache.Database
import com.heap.kmmpoc.shared.cache.DatabaseDriverFactory
//import com.heap.kmmpoc.shared.cache.Event
import com.heap.kmmpoc.shared.entity.Event
import com.heap.kmmpoc.shared.entity.Session
import com.heap.kmmpoc.shared.network.HeapAPI


class HeapSDK (databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = HeapAPI()
    private val sessionId = 42.toString()

    @Throws(Exception::class) suspend fun initSDK(platform: String) {
        database.createSession(Session(sessionId, platform  ))
    }
    @Throws(Exception::class) suspend fun getSessionId(): Session {
      return database.getSession(sessionId)

    }
    @Throws(Exception::class) suspend fun getEvents(): List<Event> {
        return database.getAllEvents(sessionId)

    }
    @Throws(Exception::class) suspend fun sendEvent(id: String){

        //val session = database.getSession(sessionId)
     return database.createEvent(Event(id, "null"), "42")
    }

    @Throws(Exception::class) suspend fun sendEvents(events: List<Event>) {
        val session = database.getSession(sessionId)
        return if (events.isNotEmpty()) {
           // api.sendAllEvents(events)
            print("sent events")
        } else {
           database.getAllEvents(session.ID).also {
             //  api.sendAllEvents(it)
               database.clearEvents()
           }

            print("no events")
        }
    }
}
