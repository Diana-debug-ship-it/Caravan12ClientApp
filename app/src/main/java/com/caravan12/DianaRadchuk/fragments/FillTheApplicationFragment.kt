package com.caravan12.DianaRadchuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.caravan12.DianaRadchuk.R
import com.caravan12.DianaRadchuk.data_classes.TourRequest
import com.caravan12.DianaRadchuk.databinding.FragmentFillTheApplicationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.github.muddz.styleabletoast.StyleableToast

class FillTheApplicationFragment : Fragment(){

    private lateinit var binding: FragmentFillTheApplicationBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val db = Firebase.firestore

    private var meal: String = ""
    private var rating: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFillTheApplicationBinding.inflate(inflater)

        val meals = resources.getStringArray(R.array.meals)
        val mealsAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, meals)
        binding.autoCompleteTVMeals.setAdapter(mealsAdapter)
        binding.autoCompleteTVMeals.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position)
            meal = item.toString()
        }

        val ratings = resources.getStringArray(R.array.rating)
        val ratingAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, ratings)
        binding.autoCompleteTVRating.setAdapter(ratingAdapter)
        binding.autoCompleteTVRating.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position)
            rating = item.toString()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance()
        auth = Firebase.auth

        binding.buttonSend.setOnClickListener {
            val request = getData()
            if (request!=null) {
                val tourRequest = hashMapOf(
                    "email" to request.email,
                    "from" to request.from,
                    "destination" to request.destination,
                    "dateOfDeparture" to request.dateOfDeparture,
                    "nights" to request.nights,
                    "adults" to request.adults,
                    "children" to request.children,
                    "meals" to request.meals,
                    "rating" to request.rating,
                    "comments" to request.comments
                )
                db.collection("tourRequests").add(tourRequest)

                    .addOnSuccessListener {
                        StyleableToast.makeText(requireActivity(), getString(R.string.info_wait_answer_in_email),
                            Toast.LENGTH_SHORT, R.style.successToast).show()
                        clearTheFields()
                    }
                    .addOnFailureListener {
                        StyleableToast.makeText(requireActivity(), getString(R.string.error_while_sending),
                            Toast.LENGTH_SHORT, R.style.errorToast).show()
                    }

            }
            else StyleableToast.makeText(requireActivity(), getString(R.string.error_while_sending), Toast.LENGTH_SHORT, R.style.errorToast).show()
        }
    }

    private fun clearTheFields() {
        binding.apply {
            editTextFrom.setText("")
            editTextTo.setText("")
            editTextDateOfdeparture.setText("")
            editTextNights.setText("")
            editTextPeople.setText("")
            editTextChildren.setText("")
            editTextComments.setText("")
            autoCompleteTVMeals.setText("Питание")
            autoCompleteTVRating.setText("Рейтинг отеля")
        }
    }

    private fun getData() : TourRequest? {
        binding.apply {
            val uid = auth.currentUser!!.uid
            val from = editTextFrom.text.toString()
            val where = editTextTo.text.toString()
            val date = editTextDateOfdeparture.text.toString()
            val nights = editTextNights.text.toString()
            val people = editTextPeople.text.toString()
            val children = editTextChildren.text.toString()
            val comments = editTextComments.text.toString()

            if (from.isEmpty() || where.isEmpty() || date.isEmpty() || nights.isEmpty() || people.isEmpty() || meal.isEmpty() || rating.isEmpty() || children.isEmpty()) {
                if (from.isEmpty()) {
                    editTextFrom.error = getString(R.string.error_empty_field)
                }
                if (where.isEmpty()) {
                    editTextTo.error = getString(R.string.error_empty_field)
                }
                if (date.isEmpty()) {
                    editTextDateOfdeparture.error = getString(R.string.error_empty_field)
                }
                if (nights.isEmpty()) {
                    editTextNights.error = getString(R.string.error_empty_field)
                }
                if (people.isEmpty()) {
                    editTextPeople.error = getString(R.string.error_empty_field)
                }
                if (children.isEmpty()) {
                    editTextChildren.error = getString(R.string.error_empty_field)
                }
                if (meal.isEmpty()) {
                    autoCompleteTVMeals.error = getString(R.string.choose_spinner)
                }
                if (rating.isEmpty()) {
                    autoCompleteTVRating.error = getString(R.string.choose_spinner)
                }
            } else {
                if (auth.currentUser!!.isEmailVerified) {
                    val applicantEmail = auth.currentUser!!.email.toString()
                    return TourRequest(applicantEmail, from, where, date, nights, people, comments, children, meal, rating)
                } else {
                    StyleableToast.makeText(requireActivity(), getString(R.string.warning_email_not_verified), Toast.LENGTH_SHORT, R.style.errorToast).show()
                }
            }
            return null
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FillTheApplicationFragment
    }
}