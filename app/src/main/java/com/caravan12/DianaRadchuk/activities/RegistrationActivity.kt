package com.caravan12.DianaRadchuk.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.caravan12.DianaRadchuk.R
import com.caravan12.DianaRadchuk.databinding.ActivityRegistrationBinding
import com.caravan12.DianaRadchuk.databinding.DialogLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.github.muddz.styleabletoast.StyleableToast

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var dialogBinding: DialogLoginBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val db = Firebase.firestore

    private var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()

        openLoginDialog()


        binding.edTextPhone.addTextChangedListener (object : TextWatcher {

            var sb: StringBuilder = StringBuilder()
            var ignore: Boolean = false
            private val numPlace = 'X'

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!ignore) {
                    removeFormat(s.toString())
                    applyFormat(sb.toString())
                    ignore = true
                    s?.replace(0, s.length, sb.toString())
                    ignore = false
                }
            }

            private fun removeFormat(text: String) {
                sb.setLength(0)
                for (element in text) {
                    val c: Char = element
                    if (isNumberChar(c)) sb.append(c)
                }
            }

            private fun applyFormat(text: String) {
                val template = getTemplate(text)
                sb.setLength(0)
                var textIndex = 0
                for (i in template.indices) {
                    if (textIndex<text.length) {
                        if (template[i]==numPlace) {
                            sb.append(text[textIndex])
                            textIndex++
                        } else {
                            sb.append(template[i])
                        }
                    }
                }
            }

            private fun isNumberChar(c: Char) : Boolean {return c in '0'..'9' }

            private fun getTemplate(text: String) : String {
                if (text.startsWith("7")) {
                    return "+X (XXX) XXX-XX-XX"
                } else {
                    return "+XXX (XXX) XX-XX-XX"
                }
            }
        })


    }

    private fun goToMainActivity() {
        val email = Firebase.auth.currentUser?.email.toString()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("Email", email)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
    }

    fun onClickSignUp(view: View) {
        binding.apply {
            val name = binding.edTextnName.text.toString().trim()
            val email = binding.edTextEmail.text.toString().trim()
            val number = binding.edTextPhone.text.toString().trim()
            val password = binding.edTextPassword.text.toString().trim()
            val repeatedPassword = binding.edTextRepeatPassword.text.toString().trim()
            if (name.isEmpty() || email.isEmpty() || number.isEmpty() || password.isEmpty() || repeatedPassword.isEmpty()) {
                if (name.isEmpty()) {
                    edTextnName.error = getString(R.string.error_et_name)
                }
                if (email.isEmpty()) {
                    edTextEmail.error = getString(R.string.error_ed_email)
                }
                if (number.isEmpty()) {
                    edTextPhone.error = getString(R.string.error_et_number)
                }
                if (password.isEmpty()) {
                    edTextPassword.error = getString(R.string.error_et_password)
                }
                if (repeatedPassword.isEmpty()) {
                    edTextRepeatPassword.error = getString(R.string.error_et_repeatedpassword)
                }
            } else if(!email.matches(emailPattern.toRegex())) {
                edTextEmail.error = getString(R.string.error_wrongFormat_email)
            } else if (number.length!=11) {
                edTextPhone.error = getString(R.string.error_wrongFormat_number)
            } else if (password.length<6) {
                edTextPassword.error = getString(R.string.error_wrongFormat_password)
            } else if (password!=repeatedPassword) {
                edTextRepeatPassword.error = getString(R.string.error_passwords_not_match)
            } else if (!checkBoxAgreement.isChecked) {
                checkBoxAgreement.error = getString(R.string.error_checkbox_not_checked)
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        val uid = auth.currentUser!!.uid
                        val user = hashMapOf(
                            "name" to name,
                            "number" to number,
                            "email" to email)

                        db.collection("users").document(uid).set(user)
                            .addOnSuccessListener {
                                StyleableToast.makeText(this@RegistrationActivity, getString(R.string.strSuccessSignUp),
                                    Toast.LENGTH_SHORT, R.style
                                    .successToast).show()
                                getCurrentUser()

                                verifyEmail()
                                goToMainActivity()
                            }
                            .addOnFailureListener {
                                StyleableToast.makeText(this@RegistrationActivity, getString(R.string.str_error),
                                    Toast.LENGTH_SHORT, R.style.errorToast).show()
                                deleteUser()
                            }
                    }
                    .addOnFailureListener {
                        StyleableToast.makeText(this@RegistrationActivity, getString(R.string.str_error),
                            Toast.LENGTH_SHORT, R.style.errorToast).show()
                    }
            }
        }
    }
//

    fun onClickSignIn(view: View) {
        val email = dialogBinding.editTextEmail.text.toString().trim()
        val password = dialogBinding.editTextPassword.text.toString().trim()

        if (!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        getCurrentUser()
                        goToMainActivity()
                    } else {
                        StyleableToast.makeText(this, getString(R.string.str_signIn_error), Toast.LENGTH_SHORT, R.style.errorToast).show()
                    }
                }
        } else {
            StyleableToast.makeText(this, getString(R.string.empty_fields_warning), Toast.LENGTH_SHORT, R.style.infoToast).show()
        }
    }

    private fun getCurrentUser(){
        val user = Firebase.auth.currentUser
        if (user!=null) {
            StyleableToast.makeText(this, getString(R.string.info_login_as)+user.email, Toast.LENGTH_SHORT, R.style.successToast).show()
        }
    }

    private fun deleteUser(){
        val user = Firebase.auth.currentUser!!
        user.delete()
            .addOnCompleteListener{ task->
                if (task.isSuccessful)
                    StyleableToast.makeText(this, getString(R.string.info_account_deleted), Toast.LENGTH_SHORT, R.style.infoToast).show()
            }
    }

    private fun verifyEmail() {
        val user = Firebase.auth.currentUser!!
        auth.useAppLanguage()
        user.sendEmailVerification()
            .addOnSuccessListener {
                StyleableToast.makeText(this@RegistrationActivity,
                    getString(R.string.to_email) + user.email + getString(R.string.email_verification_sent), Toast.LENGTH_SHORT, R.style.successToast).show()
            }
            .addOnFailureListener {
                StyleableToast.makeText(this@RegistrationActivity, getString(R.string.error_while_sending), Toast.LENGTH_SHORT, R.style.errorToast).show()
            }
    }

    private fun openLoginDialog(){
        binding.textViewBackToLogin.setOnClickListener{
            dialogBinding = DialogLoginBinding.inflate(layoutInflater)
            val mBuilder = AlertDialog.Builder(this).setView(dialogBinding.root)
            val mAlertDialog = mBuilder.show()

            dialogBinding.textViewForgotPassword.setOnClickListener{
                forgotPassword()
            }
        }
    }

    private fun forgotPassword(){
        val emailForReset = dialogBinding.editTextEmail.text
        if (emailForReset.isNotEmpty()) {
            auth.useAppLanguage()
            auth.sendPasswordResetEmail(emailForReset.toString().trim())
                .addOnSuccessListener {
                    StyleableToast.makeText(
                        this,
                        getString(R.string.info_password_reset),
                        Toast.LENGTH_SHORT,
                        R.style.successToast
                    ).show()
                }
                .addOnFailureListener {
                    StyleableToast.makeText(this, getString(R.string.warn_user_not_found), Toast.LENGTH_SHORT, R.style.errorToast).show()
                }
        } else {
            StyleableToast.makeText(this, getString(R.string.fill_email_field), Toast.LENGTH_SHORT, R.style.infoToast).show()
        }
    }
}