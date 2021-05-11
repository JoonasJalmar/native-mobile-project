package com.example.quizapp

import android.os.Build
import android.os.ProxyFileDescriptorCallback
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.io.IOUtils
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import kotlin.concurrent.thread

fun constructURL(amount: Int = 10, category: Int = 9, difficulty: String = "medium", type: String = "boolean"): URL {
    val baseUrl = "https://opentdb.com/"
    val args = "api.php?amount=$amount&category=$category&difficulty=$difficulty&type=$type"
    return URL(baseUrl + args)
}

@RequiresApi(Build.VERSION_CODES.KITKAT)
fun URL.downloadUrl(): String {
    val conn = this.openConnection() as HttpURLConnection
    val inputStream = conn.inputStream
    var result = ""
    inputStream.use {
        result = IOUtils.toString(it, StandardCharsets.UTF_8)
    }
    return result
}

@RequiresApi(Build.VERSION_CODES.KITKAT)
fun URL.downloadUrlAsync(callback: (result: String) -> Unit): Unit {
    thread {
        callback(this.downloadUrl())
    }
}
