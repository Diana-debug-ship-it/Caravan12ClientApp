package com.caravan12.DianaRadchuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.DianaRadchuk.R
import com.caravan12.DianaRadchuk.adapters.EventsRVAdapter
import com.caravan12.DianaRadchuk.adapters.UserApplicationRVAdapter
import com.caravan12.DianaRadchuk.data_classes.EventInfo
import com.caravan12.DianaRadchuk.databinding.FragmentEventsBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.github.muddz.styleabletoast.StyleableToast

class EventsFragment : Fragment() {

    private lateinit var adapter: EventsRVAdapter
    private lateinit var rvEvents: RecyclerView
    private lateinit var eventsList: ArrayList<EventInfo>

    private lateinit var binding: FragmentEventsBinding

    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEventsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventsList = arrayListOf<EventInfo>();

        getEventsList()
        setData()
    }

    private fun getEventsList() {
        db.collection("events").get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val event: EventInfo? = data.toObject(EventInfo::class.java)
                        if (event!=null) {
                            eventsList.add(event)
                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                StyleableToast.makeText(requireActivity(), "Произошла ошибка при получении списка акций", Toast.LENGTH_SHORT, R.style.errorToast).show()
            }
    }

    private fun setData() {
        val layoutManager = LinearLayoutManager(context)
        rvEvents = binding.rvCurrentEvents
        rvEvents.layoutManager = layoutManager
        rvEvents.setHasFixedSize(false)

        adapter =EventsRVAdapter(eventsList)
        rvEvents.adapter = adapter
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            EventsFragment().apply {
            }
    }
}