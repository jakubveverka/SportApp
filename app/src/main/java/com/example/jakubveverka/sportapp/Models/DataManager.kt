package com.example.jakubveverka.sportapp.Models

import android.content.Context
import com.example.jakubveverka.sportapp.Adapters.EventsRecyclerViewAdapter
import com.example.jakubveverka.sportapp.Entities.Event
import java.util.*
import android.widget.Toast
import com.example.jakubveverka.sportapp.Utils.MyLog
import com.google.firebase.database.*


/**
 * Created by jakubveverka on 14.06.17.
 * Class for data managment (Firebase and local SQL db)
 */
object DataManager {

    var context: Context? = null
    var userUid: String? = null

    val childEventListener: ChildEventListener by lazy {
        object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                MyLog.d("onChildAdded:" + dataSnapshot.key)
                val newEvent = dataSnapshot.getValue(Event::class.java) ?: return
                newEvent.firebaseKey = dataSnapshot.key
                addNewEventToOrderedList(newEvent)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                MyLog.d("onChildChanged:" + dataSnapshot.key)
                val changedEvent = dataSnapshot.getValue(Event::class.java) ?: return
                changedEvent.firebaseKey = dataSnapshot.key
                updateEventInList(changedEvent)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                MyLog.d("onChildRemoved:" + dataSnapshot.key)
                val eventKey = dataSnapshot.key
                removeEventInListWithKey(eventKey)
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                MyLog.d("onChildMoved:" + dataSnapshot.key)
                val movedEvent = dataSnapshot.getValue(Event::class.java) ?: return
                movedEvent.firebaseKey = dataSnapshot.key
                removeEventInListWithKey(movedEvent.firebaseKey!!)
                addNewEventToOrderedList(movedEvent)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                MyLog.d("onCancelled " + databaseError.toException().toString())
                Toast.makeText(context, "Failed to load events.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    var eventsRef: Query? = null

    fun init(context: Context, userUid: String): DataManager {
        this.context = context
        this.userUid = userUid
        return this
    }

    private val mEventsAdapter: EventsRecyclerViewAdapter by lazy {
        EventsRecyclerViewAdapter(context!!)
    }
    private val mEvents: LinkedList<Event> = LinkedList()

    /**
     * Method decides where to save event and saves it
     */
    fun saveEvent(event: Event): Boolean {
        when (event.storage) {
            Event.EventStorage.LOCAL -> return saveEventToDb(event)
            Event.EventStorage.FIREBASE -> return saveEventToFirebase(event)
            else -> throw Exception("Not supported EventStorage type")
        }
    }

    private fun saveEventToDb(event: Event): Boolean {
        return EventsDbHelper.getInstance(context!!).saveEvent(event)
    }

    private fun saveEventToFirebase(event: Event): Boolean {
        val database = FirebaseDatabase.getInstance().reference
        val eventsRef = database.child("users").child(event.userUid).child("events").push()
        eventsRef.setValue(event)
        return true
    }

    fun getAllEventsAdapterOfUserWithUid(): EventsRecyclerViewAdapter {
        mEvents.clear()
        getAllEventsOfUserWithUid()
        mEventsAdapter.mValues = mEvents
        return mEventsAdapter
    }

    private fun getAllEventsOfUserWithUid() {
        getLocalDbEventsOfUserWithUid()
        getFirebaseDbEventsOfUserWithUid()
    }

    fun getLocalDbEventsOfUserWithUid() {
        mEvents.addAll(EventsDbHelper.getInstance(context!!).getAllEventsOfUserWithUid(userUid!!))
    }

    /*private fun getFirebaseDbEventsOfUserWithUid(userUid: String) {
        startListeningForEvents(userUid)
    }*/

    private fun addNewEventToOrderedList(newEvent: Event) {
        var inserted = false
        var insertedIndex = 0
        MyLog.d("mEventsSize in addNewEventToOrderedList: ${mEvents.size}")
        val iterator = mEvents.listIterator(mEvents.size)
        while(iterator.hasPrevious()) {
            val event = iterator.previous()
            if(newEvent.startTime >= event.startTime) {
                if(iterator.hasNext()) iterator.next()
                iterator.add(newEvent)
                inserted = true
                insertedIndex = iterator.nextIndex() - 1
                MyLog.d("inserting at index: $insertedIndex")
                break
            }
        }
        if(!inserted) iterator.add(newEvent)
        mEventsAdapter.notifyItemInserted(insertedIndex)
    }

    private fun updateEventInList(changedEvent: Event) {
        val iterator = mEvents.listIterator(mEvents.size)
        while(iterator.hasPrevious()) {
            val event = iterator.previous()
            if(event.firebaseKey == changedEvent.firebaseKey) {
                iterator.set(changedEvent)
                mEventsAdapter.notifyItemChanged(iterator.nextIndex())
                break
            }
        }
    }

    private fun removeEventInListWithKey(key: String) {
        val iterator = mEvents.listIterator(mEvents.size)
        while(iterator.hasPrevious()) {
            val event = iterator.previous()
            if(event.firebaseKey == key) {
                val removedIndex = iterator.nextIndex()
                iterator.remove()
                mEventsAdapter.notifyItemRemoved(removedIndex)
                break
            }
        }
    }

    fun getFirebaseDbEventsOfUserWithUid() {
        eventsRef?.removeEventListener(childEventListener)
        val database = FirebaseDatabase.getInstance().reference
        eventsRef = database.child("users").child(userUid!!).child("events").orderByChild("startTime")
        eventsRef?.addChildEventListener(childEventListener)
    }

    fun stopListeningOnFirebaseDb() {
        eventsRef?.removeEventListener(childEventListener)
    }
}