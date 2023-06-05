package com.caravan12.DianaRadchuk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.DianaRadchuk.R
import com.caravan12.DianaRadchuk.data_classes.EventInfo

class EventsRVAdapter (private val eventsList: ArrayList<EventInfo>): RecyclerView.Adapter<EventsRVAdapter.EventsHolder>() {

    class EventsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvNameOfEvent)
        val description: TextView = itemView.findViewById(R.id.tvDescriptionOfEvent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_event_layout, parent, false)
        return EventsHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventsHolder, position: Int) {
        val currentItem = eventsList[position]
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }
}
