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
import org.json.JSONObject
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var etNumberOfQuestions : EditText
    lateinit var tbMultipleChoice : ToggleButton
    lateinit var rgGategories : RadioGroup
    lateinit var rgDifficulty : RadioGroup
    lateinit var startButton : Button

    lateinit var questionObject : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.etNumberOfQuestions = findViewById(R.id.etNumberOfQuestions)
        this.tbMultipleChoice = findViewById(R.id.tbMultipleChoice)
        this.rgGategories = findViewById(R.id.rgGategories)
        this.rgDifficulty = findViewById(R.id.rgDifficulty)
        this.startButton = findViewById(R.id.startButton)

        constructURL().downloadUrlAsync {
            Log.d("TAG", it)
            questionObject = it
            Log.d("TAG", questionObject)
        }

        startButton.setOnClickListener {
            var questions : String = ""
            constructURL().downloadUrlAsync {
                val rootObj = JSONObject(it)
                val questionsArray = rootObj.getJSONArray("results")
                for (i in 0..questionsArray.length() -1) {
                    val questionObject =  questionsArray.getJSONObject(i)
                    val question = questionObject.getString("question")

                    questions += "\n $question"
                    Log.d("TAG", question.toString())

                }

                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("questions", questions)
                startActivity(intent)
            }

        }

    }

}
