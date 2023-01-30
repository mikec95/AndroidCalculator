package com.example.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var textView: TextView? = null
    var lastNumeric = false
    var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.result_textview)
    }

    fun onDigit(view: View) {
        textView?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }
    fun onClear(view: View) {
        textView?.text = ""
        lastDot = false
        lastNumeric = true
    }
    fun onDecimal(view: View) {
        if(!lastDot && lastNumeric) {
            textView?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
    fun onOperator(view: View) {
        textView?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())) {
                textView?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }
    fun onEqual(view: View) {
        var prefix = ""
        if(lastNumeric) {
            var textViewString = textView?.text.toString()

            try{
                if(textViewString.startsWith("-")) {
                    prefix = "-"
                    textViewString = textViewString.substring(1)
                }
                if(textViewString.contains("-")) {
                    val splitValue = textViewString.split("-")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    textView?.text =removeDecimal((one.toDouble() - two.toDouble()).toString())
                }else if(textViewString.contains("+")) {
                    val splitValue = textViewString.split("+")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    textView?.text =removeDecimal((one.toDouble() + two.toDouble()).toString())
                }else if(textViewString.contains("/")) {
                    val splitValue = textViewString.split("/")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    textView?.text =removeDecimal((one.toDouble() / two.toDouble()).toString())
                } else if(textViewString.contains("*")) {
                    val splitValue = textViewString.split("*")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    textView?.text =removeDecimal((one.toDouble() * two.toDouble()).toString())
                }
            }catch (e: Exception){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun removeDecimal(result: String): String {
        var value = result
        if(result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }
    private fun isOperatorAdded(str: String): Boolean {
        return if(str.startsWith("-")) {
            false
        } else {
            str.contains("/") ||
                    str.contains("+") ||
                    str.contains("*") ||
                    str.contains("-")
        }
    }
}