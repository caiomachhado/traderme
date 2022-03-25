package com.example.traderme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TeacherActivity : AppCompatActivity() {

    private var btn_addCourse: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher)

        initialise()

    }

    private fun initialise(){

        btn_addCourse = findViewById(R.id.btn_addNewCourse)

        btn_addCourse!!.setOnClickListener { startActivity(Intent(this@TeacherActivity, AddCourseActivity::class.java)) }

    }

}