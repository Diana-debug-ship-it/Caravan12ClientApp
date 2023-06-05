package com.caravan12.DianaRadchuk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.DianaRadchuk.R
import com.caravan12.DianaRadchuk.data_classes.TopDirection

class DirectionsRVAdapter(private val topDirectionList: ArrayList<TopDirection>): RecyclerView.Adapter<DirectionsRVAdapter.DirectionsHolder>(){

    class DirectionsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageID : ImageView = itemView.findViewById(R.id.ivDirectionImage)
        val directionTitle : TextView = itemView.findViewById(R.id.textViewDirection)
        val directionDescription : TextView = itemView.findViewById(R.id.textViewDescription)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectionsHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_direction_layout, parent, false)
        return DirectionsHolder(itemView)
    }

    override fun onBindViewHolder(holder: DirectionsHolder, position: Int) {
        val currentItem = topDirectionList[position]
        holder.directionTitle.text = currentItem.title
        holder.directionDescription.text = currentItem.description
        holder.imageID.setImageResource(currentItem.imageId)
    }

    override fun getItemCount(): Int {
        return topDirectionList.size
    }
}