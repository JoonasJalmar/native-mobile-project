 package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var currentPosition: Int = 1
    private var questionList: ArrayList<Question>? = null
    private var selectedOptionPosition: String = ""
    private var correctCounter = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val numberOfQuestions: Int? = intent.getIntExtra("numberOfQuestions", 0)
        val difficulty: String? = intent.getStringExtra("difficulty")
        val category: Int? = intent.getIntExtra("category", 0)

        // Safely constructing question list.
        if (difficulty != null && category != null) {
            questionList = numberOfQuestions?.let { QuestionBuilder.getQuestions(amount = it, difficulty = difficulty, category = category) }
        }

        // Better way for waiting on questions?
        Thread.sleep(1000)

//        Log.d("TAG", questionList.toString())
//        val sq = questionList!!.size
//        Log.d("TAG", "Size of list: $sq")

        // If there are no questions returned, it usually means there weren't enough questions.
        if (questionList!!.size > 1) {
            setQuestion()
        } else {
            Toast.makeText(
                this,
                "There are not enough questions with these settings. Try lowering the amount of questions.", Toast.LENGTH_LONG
            ).show()
        }

        tvOptionOne.setOnClickListener(this)
        tvOptionTwo.setOnClickListener(this)
        btSubmit.setOnClickListener(this)
    }

    private fun setQuestion() {
        val question = questionList!![currentPosition-1]

        // Sets up options
        defaultOptionsView()

        if (currentPosition == questionList!!.size) {
            btSubmit.text="Finish"
        } else {
            btSubmit.text="Submit"
        }

        tvProgress.text = "$currentPosition" + "/" + questionList!!.size
        tvQuestion.text = question.question
        tvOptionOne.text= question.correct_answer
        tvOptionTwo.text = question.incorrect_answers
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id) {

                // Setting the selected option.
                R.id.tvOptionOne -> {
                    selectedOptionView(tvOptionOne, "True")
                }
                R.id.tvOptionTwo -> {
                    selectedOptionView(tvOptionTwo, "False")
                }

                R.id.btSubmit -> {
                    if (selectedOptionPosition == "") {
                        when {
                            currentPosition <= questionList!!.size -> {
                                setQuestion()
                            }
                            else -> {
                                Toast.makeText(
                                    this,
                                    "Finished. You got $correctCounter / ${questionList!!.size} questions correct!", Toast.LENGTH_LONG
                                ).show()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    } else {
                        val question = questionList?.get(currentPosition - 1)
                        if (question!!.correct_answer != selectedOptionPosition) {
                            answerView(selectedOptionPosition, R.drawable.wrong_option_border_bg)
                        }

                        if (question.correct_answer ==  selectedOptionPosition) {
                                correctCounter += 1
                                answerView(question.correct_answer, R.drawable.correct_option_border_bg)
                        }

                        if (currentPosition == questionList!!.size) {
                            btSubmit.text = "finish"
                        } else {
                            btSubmit.text = "Next Question"
                        }
                        selectedOptionPosition = ""
                        currentPosition += 1
                    }
                }
            }
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, tvOptionOne)
        options.add(1, tvOptionTwo)

        for(option in options) {
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOption: String) {
        defaultOptionsView()
        selectedOptionPosition = selectedOption
        tv.setBackgroundColor(Color.GRAY)
    }

    private fun answerView(answer: String, drawableView: Int) {
        when(answer) {
            "True" -> {
                tvOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            }

            "False" -> {
                tvOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }
}
