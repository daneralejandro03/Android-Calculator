package com.example.calculator

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    val plus = "+"
    val subtraction = "-"
    val multiplication = "*"
    val division = "/"
    val percentage = "%"

    var operationCurrent = ""

    var firstNumber: Double = Double.NaN
    var secondNumber: Double = Double.NaN

    lateinit var TextViewTemp: TextView
    lateinit var TextViewResult: TextView

    lateinit var formatDecimal: DecimalFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        formatDecimal = DecimalFormat("#.#########")

        TextViewTemp = findViewById(R.id.textViewTemp)
        TextViewResult = findViewById(R.id.textViewResult)
    }

    fun changeOperator(button: View) {
        calculate()
        val buttonAux: Button = button as Button

        println("Operación actual: $operationCurrent")
        println("Primer número: $firstNumber")
        println("Segundo número: $secondNumber")
        println("Boton: ${buttonAux.text.toString().trim()}")

        operationCurrent = when (buttonAux.text.toString().trim()) {
            "÷" -> division
            "X" -> multiplication
            else -> buttonAux.text.toString().trim()
        }

        TextViewResult.text = formatDecimal.format(firstNumber) + operationCurrent
        TextViewTemp.text = ""
    }

    fun calculate() {
        if (!firstNumber.isNaN()) {
            try {
                secondNumber = TextViewTemp.text.toString().toDouble()
                TextViewTemp.text = ""

                firstNumber = when (operationCurrent) {
                    plus -> firstNumber + secondNumber
                    subtraction -> firstNumber - secondNumber
                    multiplication -> firstNumber * secondNumber
                    division -> firstNumber / secondNumber
                    percentage -> firstNumber % secondNumber
                    else -> {
                        Log.e("ERROR", "No se ha seleccionado una operación $operationCurrent")
                        return
                    }
                }
            } catch (e: NumberFormatException) {
                Log.e("ERROR", "Formato de número no válido")
            }
        } else {
            try {
                firstNumber = TextViewTemp.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                Log.e("ERROR", "Formato de número no válido")
            }
        }
    }

    fun selectNumber(button: View) {
        val buttonAux: Button = button as Button
        val newText = buttonAux.text.toString()
        if (newText.all { it.isDigit() }) {
            TextViewTemp.text = TextViewTemp.text.toString() + newText
        } else {
            Toast.makeText(this, "Solo se permiten números", Toast.LENGTH_SHORT).show()
        }
    }
}
