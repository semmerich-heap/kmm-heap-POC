package com.heap.kmmpoc.shared.cache

import com.heap.kmmpoc.shared.entity.HEvent
import com.heap.kmmpoc.shared.entity.HSession

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
    internal  fun getAllEvents(sessionId: String?): List<HEvent> {
        return dbQuery.selectAllEventInfo(::mapEventSelection).executeAsList() as List<HEvent>
    }

    private fun mapEventSelection(ID: String, eventText: String?): HEvent {
        return HEvent(
            ID = ID,
        eventText = eventText!!
        )
    }
    internal fun getSession(id: String): HSession {
        val result = dbQuery.selectSessionById(id, ::mapSessionSelecting).executeAsOneOrNull()
        return result as HSession
    }

    private fun mapSessionSelecting(
        ID: String,
        platform: String?
    ): HSession {
        return HSession(
            SID = ID,
            platform = platform!!
        )
    }
    internal fun createEvents(events: List<HEvent>) {
        dbQuery.transaction {
            events.forEach { event ->
                val eventFromDB = dbQuery.selectEventById(event.ID).executeAsOneOrNull()
                if (eventFromDB == null) {
                    insertEvent(event)
                }
            }
        }
    }
    internal fun createEvent(event: HEvent, sessionId: String) {
        dbQuery.transaction {
            val eventFromDB = dbQuery.selectEventById(event.ID).executeAsOneOrNull()
            if (eventFromDB == null) {
                insertEvent(event)
            }
        }
    }
    internal fun createSession(session: HSession) {
        dbQuery.transaction {
            val sessionFromDB = dbQuery.selectSessionById(session.SID).executeAsOneOrNull()
            if  (sessionFromDB == null) {
                insertSession(session)
            }

        }
    }

    private fun insertEvent(event: HEvent) {
        dbQuery.insertEvent(
            ID = event.ID,
            eventText = event.eventText
        )
    }

    private fun insertSession(session: HSession) {
        dbQuery.insertSession(
            ID = session.SID,
            platform = session.platform
        )
    }
}