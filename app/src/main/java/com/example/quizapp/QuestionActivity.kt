package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class QuestionActivity : AppCompatActivity() {

    lateinit var receiver_msg: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        this.receiver_msg = findViewById(R.id.receivedValueId)

        val intent: Intent = getIntent()

        var str : String? = intent.getStringExtra("questions")

        receiver_msg.text = str
    }
}