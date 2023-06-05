package com.caravan12.DianaRadchuk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.DianaRadchuk.R
import com.caravan12.DianaRadchuk.data_classes.TourRequest
import com.google.firebase.firestore.FirebaseFirestore


class UserApplicationRVAdapter (private val userRequestsList: ArrayList<TourRequest>): RecyclerView.Adapter<UserApplicationRVAdapter.UserRequestsHolder>() {

    private lateinit var db: FirebaseFirestore

    class UserRequestsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvWhere: TextView = itemView.findViewById(R.id.tvWhere)
        val tvFrom: TextView = itemView.findViewById(R.id.tvFrom)
        val tvAdults: TextView = itemView.findViewById(R.id.tvPeople)
        val tvChildren: TextView = itemView.findViewById(R.id.tvChildren)
        val tvNights: TextView = itemView.findViewById(R.id.tvNights)
        val tvDate: TextView = itemView.findViewById(R.id.tvDateOfDeparture)
        val tvMeals: TextView = itemView.findViewById(R.id.tvMeals)
        val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        val tvWishes: TextView = itemView.findViewById(R.id.tvComments)
        val tvDeleteApplication: TextView = itemView.findViewById(R.id.tvDeleteApplication)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRequestsHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_application_layout, parent, false)
        return UserRequestsHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserRequestsHolder, position: Int) {
        val currentItem = userRequestsList[position]
        holder.tvDate.text = currentItem.dateOfDeparture
        holder.tvWhere.text = currentItem.destination
        holder.tvFrom.text = currentItem.from
        holder.tvAdults.text = currentItem.adults
        holder.tvChildren.text = currentItem.children
        holder.tvNights.text = currentItem.nights
        holder.tvMeals.text = currentItem.meals
        holder.tvRating.text = currentItem.rating
        holder.tvWishes.text = currentItem.comments

        holder.tvDeleteApplication.setOnClickListener{
            db = FirebaseFirestore.getInstance()
            userRequestsList[position].id?.let { it1 -> db.collection("tourRequests").document(it1).delete()
                .addOnSuccessListener {
                    userRequestsList.remove(userRequestsList[position])
                    notifyDataSetChanged()
                }}
        }
    }

    override fun getItemCount(): Int {
        return userRequestsList.size
    }

}