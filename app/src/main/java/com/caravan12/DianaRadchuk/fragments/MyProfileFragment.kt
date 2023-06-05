package com.caravan12.DianaRadchuk.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.DianaRadchuk.R
import com.caravan12.DianaRadchuk.activities.RegistrationActivity
import com.caravan12.DianaRadchuk.adapters.UserApplicationRVAdapter
import com.caravan12.DianaRadchuk.data_classes.TourRequest
import com.caravan12.DianaRadchuk.data_classes.UserInfo
import com.caravan12.DianaRadchuk.databinding.FragmentMyProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import io.github.muddz.styleabletoast.StyleableToast
import java.util.EventListener

class MyProfileFragment : Fragment() {

    private lateinit var adapter: UserApplicationRVAdapter
    private lateinit var rvUserApplications: RecyclerView
    private lateinit var userRequestsList: ArrayList<TourRequest>

    private lateinit var binding: FragmentMyProfileBinding

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    private lateinit var uid: String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyProfileBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userRequestsList = arrayListOf<TourRequest>()

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        if (uid.isNotEmpty()) {
            getUserData()
            getUsersApplications()
            setData()
        }

        binding.buttonResendEmailVerification.setOnClickListener{
            onClickResendEmailVerification()
        }

        binding.buttonSignOut.setOnClickListener {
            onClickSignOut()
        }
        }

    private fun getUserData() {
        val userInformationDoc = db.collection("users").document(uid)
        userInformationDoc.get()
            .addOnSuccessListener { document ->
                if (document!=null) {
                    val userName = document.data!!["name"].toString()
                    val userEmail = document.data!!["email"].toString()
                    val userPhone = document.data!!["number"].toString()

                    binding.tvUserName.text = userName
                    binding.tvEmail.text = userEmail
                    binding.tvPhoneNumber.text = userPhone
                }
            }
            .addOnFailureListener {
                StyleableToast.makeText(requireActivity(), getString(R.string.error_try_to_restart),
                    Toast.LENGTH_SHORT, R.style.errorToast).show()
            }
    }

    private fun setData() {
        val layoutManager = LinearLayoutManager(context)
        rvUserApplications = binding.rvUserApplications
        rvUserApplications.layoutManager = layoutManager
        rvUserApplications.setHasFixedSize(false)

        adapter = UserApplicationRVAdapter(userRequestsList)
        rvUserApplications.adapter = adapter
    }


    companion object {
        @JvmStatic
        fun newInstance() = MyProfileFragment
    }

    private fun onClickSignOut() {
        auth.signOut()
        startActivity(Intent(activity, RegistrationActivity::class.java))
    }

    private fun onClickResendEmailVerification() {
        val user = auth.currentUser!!
        if (user.isEmailVerified) {
            StyleableToast.makeText(requireActivity(), "Электронная почта уже подтверждена", Toast.LENGTH_SHORT, R.style.infoToast).show()
        } else {
            user.sendEmailVerification()
                .addOnSuccessListener {
                    StyleableToast.makeText(
                        requireActivity(),
                        "На почту ${user.email} было оптравлено письмо для подтверждения адреса электронной почты",
                        Toast.LENGTH_SHORT,
                        R.style.successToast
                    ).show()
                }
                .addOnFailureListener{
                    StyleableToast.makeText(requireActivity(), "Произошла ошибка при отправке письма", Toast.LENGTH_SHORT, R.style.errorToast).show()
                }
        }
    }

    private fun getUsersApplications(){
        val user = auth.currentUser!!
        db.collection("tourRequests").whereEqualTo("email", user.email).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val documentId: String = data.id
                        val userTourRequest: TourRequest? = data.toObject(TourRequest::class.java)
                        if (userTourRequest!=null){
                            userTourRequest.id = documentId
                            userRequestsList.add(userTourRequest)
                        }
                    }
                }
                adapter.notifyDataSetChanged()
        }
            .addOnFailureListener {
                StyleableToast.makeText(requireContext(), getString(R.string.error_loadind_data),
                    Toast.LENGTH_SHORT, R.style.errorToast).show()
            }
    }





}