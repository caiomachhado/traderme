package com.example.traderme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private var btn_login: Button? = null
    private var btn_newAccount: Button? = null
    private var btn_newTeacher: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialise()

    }

    private fun initialise(){

        btn_login = findViewById(R.id.login_btn)
        btn_newAccount = findViewById(R.id.newAccount_btn)
        btn_newTeacher = findViewById(R.id.btn_sendNewTeacher)

        btn_login!!.setOnClickListener{ startActivity(Intent(this@MainActivity, LoginActivity::class.java)) }
        btn_newAccount!!.setOnClickListener{ startActivity(Intent(this@MainActivity, CreateAccountActivity::class.java)) }
        btn_newTeacher!!.setOnClickListener { startActivity(Intent(this@MainActivity, TeacherCreateAccountActivity::class.java)) }

    }

}