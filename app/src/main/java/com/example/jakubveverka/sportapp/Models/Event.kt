package com.example.jakubveverka.sportapp.Models

import android.provider.BaseColumns

/**
 * Created by jakubveverka on 12.06.17.
 */
enum class EventStorage { LOCAL, FIREBASE }

class Event(name: String, place: String, startTime: String, endTime: String, storage: EventStorage) {
    companion object {
        val TABLE_NAME = "event"
        val COLUMN_ID = "_id"
        val COLUMN_NAME = "name"
        val COLUMN_PLACE = "place"
        val COLUMN_START_TIME = "start_time"
        val COLUMN_END_TIME = "end_time"

        val SQL_CREATE_EVENTS = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT," +
                COLUMN_PLACE + " TEXT," +
                COLUMN_START_TIME + " TEXT," +
                COLUMN_END_TIME + " TEXT)"

        val SQL_DELETE_EVENTS = "DROP TABLE IF EXISTS " + TABLE_NAME
    }
}