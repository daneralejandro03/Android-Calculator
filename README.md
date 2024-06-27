Copiar código
# Calculadora Android

Esta es una aplicación de calculadora simple desarrollada para la plataforma Android utilizando Kotlin. La calculadora admite las operaciones básicas de suma, resta, multiplicación, división y porcentaje, y permite eliminar el último dígito ingresado o limpiar todos los datos.

## Características

- **Operaciones Básicas:** Suma, resta, multiplicación, división y porcentaje.
- **Eliminar:** Borra el último dígito ingresado.
- **Borrar Todo:** Resetea todos los valores y operaciones.
- **Formato Decimal:** Muestra los resultados con hasta 9 decimales de precisión.

## Instalación

1. Clona este repositorio en tu máquina local.
   ```bash
   git clone https://github.com/tu-usuario/calculadora-android.git
Abre el proyecto en Android Studio.
Conecta tu dispositivo Android o utiliza un emulador.
Ejecuta la aplicación.
Uso
Ingreso de Números: Presiona los botones numéricos para ingresar el primer número.
Seleccionar Operador: Presiona el botón de la operación que deseas realizar (+, -, *, ÷, %).
Ingreso del Segundo Número: Ingresa el segundo número.
Resultado: Presiona el botón '=' para ver el resultado de la operación.
Eliminar Último Dígito: Presiona el botón 'C' para eliminar el último dígito ingresado.
Borrar Todo: Presiona el botón 'CA' para reiniciar la calculadora.
Código
MainActivity.kt
kotlin
Copiar código

``` 
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
    val delete = "C"
    val clearAll = "CA"

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
        if (TextViewTemp.text.isNotEmpty() || firstNumber.toString() != "NaN") {
            calculate()
            val buttonAux: Button = button as Button
            operationCurrent = when (buttonAux.text.toString().trim()) {
                "÷" -> division
                "X" -> multiplication
                else -> buttonAux.text.toString().trim()
            }

            TextViewResult.text = formatDecimal.format(firstNumber) + operationCurrent
            TextViewTemp.text = ""
        }
    }

    fun calculate() {
        try {
            if (firstNumber.toString() != "NaN") {
                if (TextViewTemp.text.toString().isEmpty()) {
                    TextViewTemp.text = TextViewResult.text.toString()
                }
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
        } catch (e: Exception) {
            Log.e("ERROR", "Ocurrió un error en la función calculate")
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

    fun delete(button: View) {
        val buttonAux: Button = button as Button

        if (buttonAux.text.toString().trim() == delete) {
            if (TextViewTemp.text.isNotEmpty()) {
                TextViewTemp.text = TextViewTemp.text.dropLast(1)
            } else {
                resetCalculator()
            }
        } else if (buttonAux.text.toString().trim() == clearAll) {
            resetCalculator()
        }
    }

    fun equal(button: View) {
        calculate()
        TextViewResult.text = formatDecimal.format(firstNumber)
        firstNumber = Double.NaN
        operationCurrent = ""
    }

    private fun resetCalculator() {
        firstNumber = Double.NaN
        secondNumber = Double.NaN
        TextViewTemp.text = ""
        TextViewResult.text = ""
    }
}
```
