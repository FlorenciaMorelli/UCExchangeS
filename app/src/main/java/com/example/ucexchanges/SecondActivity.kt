package com.example.ucexchanges

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.lang.Math.round

class SecondActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get the data from the intent
        val amount = intent.getDoubleExtra("amount", 0.0)
        val fromCurrency = intent.getStringExtra("from_currency") ?: ""
        val toCurrency = intent.getStringExtra("to_currency") ?: ""
        val convertedAmount = intent.getDoubleExtra("converted_amount", 0.0)
        // Format the converted amount to 5 decimal places max
        val convertedAmountString = convertedAmount.toString()
        val parts = convertedAmountString.split(".")
        val decimalPlaces = if (parts.size > 1) parts[1].length else 0
        val formatString = if(decimalPlaces < 5) "%.${decimalPlaces}f" else "%.5f"
        val formattedAmount = String.format(formatString, convertedAmount)

        // Set the text views with the data
        findViewById<TextView>(R.id.tv_amount).text = amount.toString()
        findViewById<TextView>(R.id.tv_currency).text = fromCurrency
        findViewById<TextView>(R.id.tv_amount_total).text = formattedAmount
        findViewById<TextView>(R.id.tv_currency_total).text = toCurrency

        findViewById<Button>(R.id.btn_back).setOnClickListener {
            // Finish the activity and return to the previous screen
            finish()
        }
    }
}