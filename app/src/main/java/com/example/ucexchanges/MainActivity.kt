package com.example.ucexchanges

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
//  Define variables
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var editTextAmount: EditText
    private val currencies = listOf("ARS", "EUR", "USD")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        spinnerFrom = findViewById(R.id.sp_from)
        spinnerTo = findViewById(R.id.sp_to)
        editTextAmount = findViewById(R.id.et_amount)

        // Set up spinner adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        // Set up button click listener and primary action
        findViewById<Button>(R.id.btn_convert).setOnClickListener {
            // Get user input
            val amount = editTextAmount.text.toString().toDoubleOrNull() ?: 0.0
            val fromCurrency = spinnerFrom.selectedItem.toString()
            val toCurrency = spinnerTo.selectedItem.toString()
            // Convert currency, call function
            val convertedAmount = convertCurrency(amount, fromCurrency, toCurrency)

            // Create intent to start second activity
            val intent = Intent(this, SecondActivity::class.java)
            // Pass data to second activity
            intent.putExtra("amount", amount)
            intent.putExtra("from_currency", fromCurrency)
            intent.putExtra("to_currency", toCurrency)
            intent.putExtra("converted_amount", convertedAmount)
            // Start second activity
            startActivity(intent)
        }
    }

    // Function to convert currency
    private fun convertCurrency(amount: Double, fromCurrency: String, toCurrency: String): Double {
        // Define conversion rates
        val usdRate = 883.21  // ARS to 1 USD rate
        val eurRate = 951.22  // ARS to 1 EUR rate
        val usdToEurRate = 0.93   // 1 USD to EUR rate

        // Get conversion rate based on user input
        val conversionRate = when (fromCurrency) {
            "ARS" -> when (toCurrency) {
                "USD" -> 1 / usdRate
                "EUR" -> 1 / eurRate
                else -> 1.0
            }
            "USD" -> when (toCurrency) {
                "ARS" -> usdRate
                "EUR" -> usdToEurRate
                else -> 1.0
            }
            "EUR" -> when (toCurrency) {
                "ARS" -> eurRate
                "USD" -> 1 / usdToEurRate  // Conversion rate for EUR to USD
                else -> 1.0
            }
            else -> 1.0
        }

        // Convert amount based on conversion rate
        return amount * conversionRate
    }
}