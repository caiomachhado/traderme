package com.example.traderme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CreateAccountActivity : AppCompatActivity() {

    private var edit_text_name: EditText? = null
    private var edit_text_email: EditText? = null
    private var edit_text_user: EditText? = null
    private var edit_text_password: EditText? = null
    private var btn_sendFormCreateAcc: Button? = null

    private var mDatabaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        initialise()

    }

    private fun initialise(){

        edit_text_name = findViewById(R.id.edit_text_name)
        edit_text_email = findViewById(R.id.edit_text_email)
        edit_text_user = findViewById(R.id.edit_text_user)
        edit_text_password = findViewById(R.id.edit_text_password)
        btn_sendFormCreateAcc = findViewById(R.id.btn_sendFormCreateAcc)


    }

}