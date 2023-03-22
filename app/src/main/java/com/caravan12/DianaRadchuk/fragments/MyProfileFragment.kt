package com.caravan12.DianaRadchuk.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.caravan12.DianaRadchuk.R
import com.caravan12.DianaRadchuk.activities.RegistrationActivity
import com.caravan12.DianaRadchuk.data_classes.UserInfo
import com.caravan12.DianaRadchuk.databinding.FragmentMyProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.github.muddz.styleabletoast.StyleableToast

class MyProfileFragment : Fragment() {

    private lateinit var binding: FragmentMyProfileBinding

    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var user: UserInfo
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

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        if (uid.isNotEmpty()) {
            getUserData()
        }

        binding.buttonResendEmailVerification.setOnClickListener{
            onClickResendEmailVerification()
        }

        binding.buttonSignOut.setOnClickListener {
            onClickSignOut()
        }
        }

    private fun getUserData() {
        databaseReference.child(uid).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(UserInfo::class.java)!!
                binding.tvUserName.text = user.name
                binding.tvEmail.text = user.email
                binding.tvPhoneNumber.text = user.number
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            MyProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
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
}