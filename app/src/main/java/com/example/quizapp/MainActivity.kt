package com.example.quizapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import kotlin.concurrent.thread
import org.apache.commons.io.IOUtils
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var etNumberOfQuestions : EditText
    lateinit var rgGategories : RadioGroup
    lateinit var rgDifficulty : RadioGroup
    lateinit var startButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       this.etNumberOfQuestions = findViewById(R.id.etNumberOfQuestions)
//        this.tbMultipleChoice = findViewById(R.id.tbMultipleChoice)
        this.rgGategories = findViewById(R.id.rgGategories)
        this.rgDifficulty = findViewById(R.id.rgDifficulty)
        this.startButton = findViewById(R.id.startButton)

        // For choosing category
        var category: Int? = 9
        rgGategories.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rbGeneral)
                category = 9
            if (checkedId == R.id.rbScience)
                category = 18
            if (checkedId == R.id.rbHistory)
                category = 23
            if (checkedId == R.id.rbSports)
                category = 21
            if (checkedId == R.id.rbAnimals)
                category = 27
            if (checkedId == R.id.rbPolitics)
                category = 24
            if (checkedId == R.id.rbArt)
                category = 25
            if (checkedId == R.id.rbMythology)
                category = 20
            if (checkedId == R.id.rbCelebrities)
                category = 26
            if (checkedId == R.id.rbBooks)
                category = 10
        }

        // For choosing difficulty
        var difficulty: String? = "medium"
        rgDifficulty.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.easy)
                difficulty = "easy"
            if (checkedId == R.id.medium)
                difficulty = "medium"
            if (checkedId == R.id.hard)
                difficulty = "hard"
        }

        startButton.setOnClickListener {

            // For choosing number of questions
            val numberOfQuestions: Int
            if(etNumberOfQuestions.text.length > 0) {
                numberOfQuestions = etNumberOfQuestions.text.toString().toInt()
            }else {
                numberOfQuestions = 10
                Toast.makeText(applicationContext, "Default 10 questions", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("numberOfQuestions", numberOfQuestions)
            intent.putExtra("difficulty", difficulty)
            intent.putExtra("category", category)

            startActivity(intent)
        }
    }
}
