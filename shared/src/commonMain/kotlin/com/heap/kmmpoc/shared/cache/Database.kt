package com.heap.kmmpoc.shared.cache

import com.heap.kmmpoc.shared.entity.Event
import com.heap.kmmpoc.shared.entity.Session

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllSessions()
            dbQuery.removeAllEvents()
        }
    }
    internal fun clearEvents() {
        dbQuery.transaction {
            dbQuery.removeAllEvents()
        }
    }
    internal  fun getAllEvents(sessionId: String): List<Event> {

        return dbQuery.selectAllEventInfo().executeAsList() as List<Event>
    }

    private fun mapEventSelection(ID: String, eventText: String): Event {
        return Event(
            ID = ID,
        eventText = eventText
        )
    }
    internal fun getSession(id: String): Session {
        val result = dbQuery.selectSessionById(id).executeAsOneOrNull()
        return result as Session
    }

    private fun mapSessionSelecting(
        ID: String,
        platform: String
    ): Session {
        return Session(
            ID = ID,
            platform = platform
        )
    }
    internal fun createEvents(events: List<Event>) {
        dbQuery.transaction {
            events.forEach { event ->
                val eventFromDB = dbQuery.selectEventById(event.ID).executeAsOneOrNull()
                if (eventFromDB == null) {
                    insertEvent(event)
                }
            }
        }
    }
    internal fun createEvent(event: Event, sessionId: String) {
        dbQuery.transaction {
            val eventFromDB = dbQuery.selectEventById(event.ID).executeAsOneOrNull()
            if (eventFromDB == null) {
                insertEvent(event)
            }
        }
    }
    internal fun createSession(session: Session) {
        dbQuery.transaction {
            val sessionFromDB = dbQuery.selectSessionById(session.ID).executeAsOneOrNull()
            if  (sessionFromDB == null) {
                insertSession(session)
            }

        }
    }

    private fun insertEvent(event: Event) {
        dbQuery.insertEvent(
            ID = event.ID,
            eventText = event.eventText
        )
    }

    private fun insertSession(session: Session) {
        dbQuery.insertSession(
            ID = session.ID,
            platform = session.platform
        )
    }
}