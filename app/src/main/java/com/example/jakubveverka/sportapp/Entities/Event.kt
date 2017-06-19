package com.example.jakubveverka.sportapp.Entities

import android.os.Parcel
import android.os.Parcelable
import com.example.jakubveverka.sportapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.Exclude

/**
 * Created by jakubveverka on 12.06.17.
 */
class Event(var name: String = "", var place: String = "", var startTime: Long = 0, var endTime: Long = 0, @Exclude var storage: EventStorage = Event.EventStorage.FIREBASE, @Exclude var userUid: String? = null): Parcelable {

    @Exclude var firebaseKey : String? = null

    constructor(name: String, place: String, startTime: Long, endTime: Long) : this(name, place, startTime, endTime, EventStorage.FIREBASE, FirebaseAuth.getInstance().currentUser!!.uid)

    enum class EventStorage {
        LOCAL,
        FIREBASE;

        fun getStringId(): Int {
            if(this == LOCAL) return R.string.sql_database
            else if(this == FIREBASE) return R.string.firebase_database
            else throw Exception("Not supported EventStorage")
        }
    }

    companion object {
        const val TABLE_NAME = "event"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PLACE = "place"
        const val COLUMN_START_TIME = "start_time"
        const val COLUMN_END_TIME = "end_time"
        const val COLUMN_USER_UID = "user_uid"

        const val SQL_CREATE_EVENTS = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT," +
                COLUMN_PLACE + " TEXT," +
                COLUMN_START_TIME + " INTEGER," +
                COLUMN_END_TIME + " INTEGER, " +
                COLUMN_USER_UID + " TEXT)"

        const val SQL_DELETE_EVENTS = "DROP TABLE IF EXISTS " + TABLE_NAME

        @JvmField final val CREATOR: Parcelable.Creator<Event> = object : Parcelable.Creator<Event> {
            override fun createFromParcel(`in`: Parcel): Event {
                val name = `in`.readString()
                val place = `in`.readString()
                val startTime = `in`.readLong()
                val endTime = `in`.readLong()
                val storage = EventStorage.valueOf(`in`.readString())
                val userUid = `in`.readString()
                return Event(name, place, startTime, endTime, storage, userUid)
            }

            override fun newArray(size: Int): Array<Event?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(place)
        dest?.writeLong(startTime)
        dest?.writeLong(endTime)
        dest?.writeString(storage.name)
        dest?.writeString(userUid)
    }

    override fun describeContents(): Int {
        return 0
    }

}