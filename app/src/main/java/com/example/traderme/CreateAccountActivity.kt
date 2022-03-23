package com.example.traderme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateAccountActivity : AppCompatActivity() {

    private var edit_text_name: EditText? = null
    private var edit_text_email: EditText? = null
    private var edit_text_user: EditText? = null
    private var edit_text_password: EditText? = null
    private var btn_sendFormCreateAcc: Button? = null

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "CreateAccountActivity"

    private var user: String? = null
    private var name: String? = null
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        initialise()

    }

    private fun initialise(){

        edit_text_name = findViewById(R.id.edit_text_name)
        edit_text_email = findViewById(R.id.edit_text_loginEmail)
        edit_text_user = findViewById(R.id.edit_text_user)
        edit_text_password = findViewById(R.id.edit_text_password)
        btn_sendFormCreateAcc = findViewById(R.id.btn_sendFormCreateAcc)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase?.reference?.child("User")
        mAuth = FirebaseAuth.getInstance()

        btn_sendFormCreateAcc?.setOnClickListener { createNewAccount() }

    }

    private fun createNewAccount(){

        name = edit_text_name?.text.toString()
        email = edit_text_email?.text.toString()
        user = edit_text_user?.text.toString()
        password = edit_text_password?.text.toString()

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Informações preenchidas corretamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Entre com mais detalhes", Toast.LENGTH_SHORT).show()
        }

        mAuth?.let {

            it.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener(this) { task ->

                if (task.isSuccessful){
                    Log.d(TAG, "Usuário Criado")

                    val userId = it.currentUser?.uid

                    verifyEmail()

                    val currentUserDb = userId?.let {it1 -> mDatabaseReference!!.child(it1)}
                    currentUserDb?.child("name")?.setValue(name)
                    currentUserDb?.child("email")?.setValue(email)
                    currentUserDb?.child("usuario")?.setValue(user)
                    currentUserDb?.child("password")?.setValue(password)
                    currentUserDb?.child("levelAccount")?.setValue("Student")

                    updateUserInfoandUi()

                } else {

                    Log.w(TAG, "Não possível realizar o cadastro.", task.exception)
                    Toast.makeText(this@CreateAccountActivity, "Falha na autenticação", Toast.LENGTH_SHORT).show()

                }

            }

        }

    }

    private fun verifyEmail() {
        val mUser = mAuth?.currentUser;
        mUser?.sendEmailVerification()?.addOnCompleteListener(this){
            task ->

            if(task.isSuccessful){
                Toast.makeText(this@CreateAccountActivity, "Email de verificação enviado para " + mUser.email,
                    Toast.LENGTH_SHORT).show()
            } else {
                Log.e(TAG, "Email de Verificação com Erro de Envio", task.exception)
                Toast.makeText(this@CreateAccountActivity, "Erro ao enviar o email de verificação.",
                    Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun updateUserInfoandUi(){
        val intent = Intent(this@CreateAccountActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }


}