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

    lateinit var TetxViewTemp: TextView
    lateinit var TetxViewResult: TextView

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

        TetxViewTemp = findViewById(R.id.textViewTemp)
        TetxViewResult = findViewById(R.id.textViewResult)

    }

    fun changeOperator(button: View) {

        calculate()
        val buttonAux: Button = button as Button

        println("Operación actual:" + operationCurrent)
        println("Primer número:" + firstNumber)
        println("Segundo número:" + secondNumber)
        println("Boton:" + buttonAux.text.toString().trim())

        if (buttonAux.text.toString().trim() == "÷") {
            println()
            operationCurrent = "/"
        } else if (buttonAux.text.toString().trim() == "X") {
            operationCurrent = "*"
        } else {
            operationCurrent = buttonAux.text.toString().trim()
        }

        TetxViewResult.text = formatDecimal.format(firstNumber)+ operationCurrent
        TetxViewTemp.text = ""
    }

    fun calculate() {

        if (firstNumber.toString() != "NaN") {
            secondNumber = TetxViewTemp.text.toString().toDouble();
            TetxViewTemp.text = ""

            when (operationCurrent) {
                "+" -> firstNumber = (firstNumber + secondNumber)
                "-" -> firstNumber = (firstNumber - secondNumber)
                "*" -> firstNumber = (firstNumber * secondNumber)
                "/" -> firstNumber = (firstNumber / secondNumber)
                "%" -> firstNumber = (firstNumber % secondNumber)
                else -> {
                    Log.e("ERROR", "No se ha seleccionado una operación"+ operationCurrent)
                }
            }
        } else {
            firstNumber = TetxViewTemp.text.toString().toDouble();
        }
    }


    fun selectNumber(button: View) {
        val buttonAux: Button = button as Button
        val newText = buttonAux.text.toString()
        if (newText.all { it.isDigit() }) { // Verificar si todos los caracteres son dígitos
            TetxViewTemp.text = TetxViewTemp.text.toString() + newText
        } else {
            // Manejar el caso de entrada inválida, por ejemplo:
            Toast.makeText(this, "Solo se permiten números", Toast.LENGTH_SHORT)
                .show()
        }
    }
}