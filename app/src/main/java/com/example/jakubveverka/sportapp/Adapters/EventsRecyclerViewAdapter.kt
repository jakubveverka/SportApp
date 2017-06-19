package com.example.jakubveverka.sportapp.Adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.jakubveverka.sportapp.Entities.Event
import com.example.jakubveverka.sportapp.R
import com.example.jakubveverka.sportapp.ViewModels.EventsAdapterViewModel
import java.util.*

class EventsRecyclerViewAdapter(val mContext: Context) : RecyclerView.Adapter<EventsRecyclerViewAdapter.ViewHolder>() {

    var mValues: List<Event> = LinkedList()
    val mViewModel: EventsAdapterViewModel by lazy {
        EventsAdapterViewModel(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_events_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mNameTextView.text = mValues[position].name
        holder.mPlaceTextView.text = mValues[position].place
        holder.mStartTimeTextView.text = mViewModel.formatDateInMillis(mValues[position].startTime)
        holder.mEndTimeTextView.text = mViewModel.formatDateInMillis(mValues[position].endTime)
        holder.mContainer.setCardBackgroundColor(mViewModel.getBackgroundColorForEvent(mValues[position]))
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mNameTextView: TextView
        val mPlaceTextView: TextView
        val mStartTimeTextView: TextView
        val mEndTimeTextView: TextView
        val mContainer: CardView
        var mItem: Event? = null

        init {
            mNameTextView = mView.findViewById(R.id.tw_name) as TextView
            mPlaceTextView = mView.findViewById(R.id.tw_place) as TextView
            mStartTimeTextView = mView.findViewById(R.id.tw_start_time) as TextView
            mEndTimeTextView = mView.findViewById(R.id.tw_end_time) as TextView
            mContainer = mView.findViewById(R.id.events_list_item_container) as CardView
        }

        override fun toString(): String {
            return super.toString() + " '" + mNameTextView.text + "'"
        }
    }
}
