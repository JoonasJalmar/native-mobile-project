package com.example.quizapp

import android.text.Html
import android.util.Log
import android.widget.Toast
import org.json.JSONObject

object QuestionBuilder {
    fun getQuestions(amount: Int = 10, category: Int = 9, difficulty: String = "medium", type: String = "boolean"): ArrayList<Question> {
        val questionList = ArrayList<Question>()

        constructURL(amount = amount, difficulty = difficulty, category = category).downloadUrlAsync {
            val rootObj = JSONObject(it)
            val questionsArray = rootObj.getJSONArray("results")
            val responseCode = rootObj.getString("response_code")
            Log.d("TAG", "Response code: $responseCode")
            var id = 0



            for (i in 0 until questionsArray.length()) {
                val questionObject =  questionsArray.getJSONObject(i)
                // Adding id for each question since api does not have id's.
                id += 1

                val category = questionObject.getString("category")
                val type = questionObject.getString("type")
                val difficulty = questionObject.getString("difficulty")

                // Api encodes questions with HTML codes by default.
                val encodedQuestion = questionObject.getString("question")
                val decodedQuestion = Html.fromHtml(encodedQuestion).toString()
                val question = Html.fromHtml(decodedQuestion).toString()

                var correctAnswer = questionObject.getString("correct_answer")

                // Incorrect answers come in an array. For now this works for boolean questions.
                // Need to change this when implementing multiple choice questions.
                val incAnswers = questionObject.getString("incorrect_answers")
                val incorrectAnswers = incAnswers
                    .replace("\"", "")
                    .replace("[", "")
                    .replace("]", "")

                questionList.add(Question(id, category, type, difficulty, question, correctAnswer, incorrectAnswers))
            }
        }

        return questionList
    }
}
