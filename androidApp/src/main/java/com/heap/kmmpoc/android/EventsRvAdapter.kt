package com.heap.kmmpoc.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.heap.kmmpoc.shared.entity.Event

class EventsRvAdapter(var events: List<Event>) : RecyclerView.Adapter<EventsRvAdapter.EventsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
            .run(::EventsViewHolder)
    }

    override fun getItemCount(): Int = events.count()

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bindData(events[position])
    }

    inner class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eventIdTextView = itemView.findViewById<TextView>(R.id.eventId)


        fun bindData(event: Event) {
            val ctx = itemView.context
            eventIdTextView.text = ctx.getString(R.string.event_id_field, event.ID)

        }
    }
}