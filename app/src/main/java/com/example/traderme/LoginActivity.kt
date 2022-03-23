package com.example.traderme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {

    private var edit_text_email: EditText? = null
    private var edit_text_password: EditText? = null
    private var btn_sendLogin: Button? = null

    private var TAG = "LoginActivity"

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialise()

    }

    private fun initialise(){

        edit_text_email = findViewById<EditText>(R.id.edit_text_loginEmail)
        edit_text_password = findViewById<EditText>(R.id.edit_text_loginPassword)
        btn_sendLogin = findViewById(R.id.btn_loginSend)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase?.reference?.child("Client")
        mAuth = FirebaseAuth.getInstance()

        btn_sendLogin?.setOnClickListener { loginUser() }

    }

    private fun loginUser() {
        email = edit_text_email?.text.toString()
        password = edit_text_password?.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            Toast.makeText(this@LoginActivity, "Verificando informações...", Toast.LENGTH_SHORT)
                .show()

            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        Log.d(TAG, "Logado com Sucesso!")
                        updateUi()
                    } else {
                        Log.e(TAG, "Erro ao logar.", task.exception)
                        Toast.makeText(
                            this@LoginActivity,
                            "Autenticação com Falhas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

             } else {
                 Toast.makeText(this@LoginActivity, "Email: $email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUi(){

        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser!!.uid
        Log.d(TAG, "ID do Usuário: $userId")

        val currentUserDb = mDatabaseReference!!.child(userId)
        val levelAcc = currentUserDb.child("levelAccount").get().addOnSuccessListener {
            Log.d(TAG, "Encontrado ${it.value}")
            val isOrNot = it.value

            if (isOrNot?.equals("Student") == true){
                val intent = Intent(this@LoginActivity, StudentActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@LoginActivity, TeacherActivity::class.java)
                startActivity(intent)
            }

        } .addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }

    }

}