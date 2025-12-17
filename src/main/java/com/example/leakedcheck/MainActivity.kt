package com.example.leakedcheck

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val checkButton = findViewById<Button>(R.id.checkButton)
        val aboutButton = findViewById<Button>(R.id.btnAbout)

        checkButton.setOnClickListener {
            val email = emailInput.text.toString().trim()

            if (email.isEmpty()) {
                emailInput.error = "Email cannot be empty"
                Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (isValidEmail(email)) {
                // Pass email to ResultActivity
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("EMAIL", email)
                startActivity(intent)
            } else {
                emailInput.error = "Please enter a valid email address"
                Toast.makeText(this, "Invalid Email Format", Toast.LENGTH_SHORT).show()
            }
        }

        aboutButton.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}