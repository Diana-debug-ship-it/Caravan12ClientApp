package com.caravan12.DianaRadchuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.caravan12.DianaRadchuk.R
import com.caravan12.DianaRadchuk.data_classes.TourRequest
import com.caravan12.DianaRadchuk.databinding.FragmentFillTheApplicationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import io.github.muddz.styleabletoast.StyleableToast

class FillTheApplicationFragment : Fragment(){

    private lateinit var binding: FragmentFillTheApplicationBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFillTheApplicationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance()
        auth = Firebase.auth

        binding.buttonSend.setOnClickListener {
            val request = getData()
            if (request!=null) {
                val databaseRef = database.reference.child("tourRequests")
                databaseRef.child(auth.currentUser!!.uid).setValue(request)
                    .addOnSuccessListener {
                        StyleableToast.makeText(requireActivity(), getString(R.string.info_wait_answer_in_email), Toast.LENGTH_SHORT, R.style.successToast).show()
                    }
                    .addOnFailureListener {
                        StyleableToast.makeText(requireActivity(), getString(R.string.error_while_sending), Toast.LENGTH_SHORT, R.style.errorToast).show()
                    }

            }
            else StyleableToast.makeText(requireActivity(), getString(R.string.error_while_sending), Toast.LENGTH_SHORT, R.style.errorToast).show()
        }
    }

    fun getData() : TourRequest? {
        binding.apply {
            val uid = auth.currentUser!!.uid
            val from = editTextFrom.text.toString()
            val where = editTextTo.text.toString()
            val date = editTextDateOfdeparture.text.toString()
            val nights = editTextNights.text.toString()
            val people = editTextPeople.text.toString()
            val comments = editTextComments.text.toString()

            if (from.isEmpty() || where.isEmpty() || date.isEmpty() || nights.isEmpty() || people.isEmpty()) {
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
            } else {
                if (auth.currentUser!!.isEmailVerified) {
                    val applicantEmail = auth.currentUser!!.email.toString()
                    return TourRequest(uid, applicantEmail, from, where, date, nights, people, comments)
                } else {
                    StyleableToast.makeText(requireActivity(), getString(R.string.warning_email_not_verified), Toast.LENGTH_SHORT, R.style.errorToast).show()
                }
            }
            return null
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FillTheApplicationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}