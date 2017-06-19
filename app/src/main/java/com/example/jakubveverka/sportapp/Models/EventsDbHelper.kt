package com.example.jakubveverka.sportapp.Models

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import com.example.jakubveverka.sportapp.Entities.Event
import java.util.*

/**
 * Created by jakubveverka on 12.06.17.
 * Classic Db helper for Event table
 */
class EventsDbHelper private constructor(context: Context) : SQLiteOpenHelper(context, EventsDbHelper.DATABASE_NAME, null, EventsDbHelper.DATABASE_VERSION) {

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "SportApp.db"

        private var instance: EventsDbHelper? = null
        @Synchronized fun getInstance(context: Context): EventsDbHelper {
            if(instance == null) instance = EventsDbHelper(context.applicationContext)
            return instance!!
        }

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Event.SQL_CREATE_EVENTS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(Event.SQL_DELETE_EVENTS)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun saveEvent(event: Event): Boolean {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(Event.COLUMN_NAME, event.name)
        values.put(Event.COLUMN_PLACE, event.place)
        values.put(Event.COLUMN_START_TIME, event.startTime)
        values.put(Event.COLUMN_END_TIME, event.endTime)
        values.put(Event.COLUMN_USER_UID, event.userUid)

        return db.insert(Event.TABLE_NAME, null, values) != -1L
    }

    fun getAllEventsOfUserWithUid(userUid: String): LinkedList<Event> {
        val db = this.readableDatabase

        val selection = Event.COLUMN_USER_UID + " = ?"
        val selectionArgs = arrayOf(userUid)

        val sortOrder = Event.COLUMN_START_TIME + " ASC"

        val cursor = db.query(
                Event.TABLE_NAME, // The table to query
                null, // The columns to return
                selection, // The columns for the WHERE clause
                selectionArgs, // don't group the rows
                null, null, // don't filter by row groups
                sortOrder // The sort order
        )// The values for the WHERE clause

        val events = LinkedList<Event>()
        val nameColumnIndex = cursor.getColumnIndexOrThrow(Event.COLUMN_NAME)
        val placeColumnIndex = cursor.getColumnIndexOrThrow(Event.COLUMN_PLACE)
        val startDateColumnIndex = cursor.getColumnIndexOrThrow(Event.COLUMN_START_TIME)
        val endDateColumnIndex = cursor.getColumnIndexOrThrow(Event.COLUMN_END_TIME)
        while (cursor.moveToNext()) {
            val name = cursor.getString(nameColumnIndex)
            val place = cursor.getString(placeColumnIndex)
            val startDate = cursor.getLong(startDateColumnIndex)
            val endDate = cursor.getLong(endDateColumnIndex)
            events.add(Event(name, place, startDate, endDate, Event.EventStorage.LOCAL, userUid))
        }
        cursor.close()

        return events
    }
}