package com.example.jakubveverka.sportapp.Models

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by jakubveverka on 12.06.17.
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


}