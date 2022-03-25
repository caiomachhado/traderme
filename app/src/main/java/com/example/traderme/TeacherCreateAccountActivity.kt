package com.example.traderme

import android.annotation.SuppressLint
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

class TeacherCreateAccountActivity : AppCompatActivity() {

    private var edit_text_name: EditText? = null
    private var edit_text_email: EditText? = null
    private var edit_text_user: EditText? = null
    private var edit_text_password: EditText? = null
    private var btn_sendFormCreateAcc: Button? = null

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "TeacherCreateAccountActivity"

    private var user: String? = null
    private var name: String? = null
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_create_account)

        initialise()

    }

    private fun initialise(){

        edit_text_name = findViewById(R.id.edit_text_teacherName)
        edit_text_email = findViewById(R.id.edit_text_teacherEmail)
        edit_text_user = findViewById(R.id.edit_text_teacherUser)
        edit_text_password = findViewById(R.id.edit_text_teacherPassword)
        btn_sendFormCreateAcc = findViewById(R.id.btn_sendFormTeacherCreateAcc)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase?.reference?.child("User")
        mAuth = FirebaseAuth.getInstance()

        btn_sendFormCreateAcc?.setOnClickListener { createNewAccount() }

    }

    @SuppressLint("LongLogTag")
    private fun createNewAccount(){

        name = edit_text_name?.text.toString()
        email = edit_text_email?.text.toString()
        user = edit_text_user?.text.toString()
        password = edit_text_password?.text.toString()


        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(user) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Informações preenchidas corretamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Entre com mais detalhes", Toast.LENGTH_SHORT).show()
        }

        mAuth?.let {

            it.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener(this) { task ->

                if (task.isSuccessful){
                    Log.d(TAG, "UsuárioCriado")

                    val userId = it.currentUser?.uid

                    verifyEmail()

                    val currentUserDb = userId?.let { it -> mDatabaseReference!!.child(it) }
                    currentUserDb?.child("name")?.setValue(name)
                    currentUserDb?.child("email")?.setValue(email)
                    currentUserDb?.child("usuario")?.setValue(user)
                    currentUserDb?.child("password")?.setValue(password)
                    currentUserDb?.child("levelAccount")?.setValue("Teacher")

                    updateUserInfoandUi()

                } else {

                    Log.w(TAG, "Não possível realizar o cadastro.", task.exception)
                    Toast.makeText(this@TeacherCreateAccountActivity, "Falha na autenticação", Toast.LENGTH_SHORT).show()

                }

            }

        }

    }

    @SuppressLint("LongLogTag")
    private fun verifyEmail() {
        val mUser = mAuth?.currentUser
        mUser?.sendEmailVerification()?.addOnCompleteListener(this){
                task ->

            if(task.isSuccessful){
                Toast.makeText(this@TeacherCreateAccountActivity, "Email de verificação enviado para " + mUser.email,
                    Toast.LENGTH_SHORT).show()
            } else {
                Log.e(TAG, "Email de Verificação com Erro de Envio", task.exception)
                Toast.makeText(this@TeacherCreateAccountActivity, "Erro ao enviar o email de verificação.",
                    Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun updateUserInfoandUi(){
        val intent = Intent(this@TeacherCreateAccountActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }


}