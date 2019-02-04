package com.yifan.kotlinfun

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var counter = 0

    fun add(view: View) {
        var textView: TextView = findViewById<TextView>(R.id.textView)
        counter++
        textView.text = counter.toString()
    }

    fun reset(view: View) {
        var textView: TextView = findViewById<TextView>(R.id.textView)
        counter = 0
        textView.text = counter.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var addButton = findViewById<Button>(R.id.addButton)
        var resetButton = findViewById<Button>(R.id.resetButton)
    }
}
