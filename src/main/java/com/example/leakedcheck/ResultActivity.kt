package com.example.leakedcheck

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class ResultActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var resultContainer: LinearLayout
    private lateinit var backButton: Button
    private lateinit var statusText: TextView
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Initialize views
        progressBar = findViewById(R.id.progressBar)
        resultContainer = findViewById(R.id.resultContainer)
        backButton = findViewById(R.id.backButton)
        statusText = findViewById(R.id.statusText)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get email from intent
        val email = intent.getStringExtra("EMAIL") ?: ""

        if (email.isNotEmpty()) {
            checkEmail(email)
        } else {
            Toast.makeText(this, "No email provided", Toast.LENGTH_SHORT).show()
            finish()
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun checkEmail(email: String) {
        progressBar.visibility = View.VISIBLE
        statusText.text = "Checking $email..."
        resultContainer.removeAllViews()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // LeakCheck API endpoint
                val url = "https://leakcheck.io/api/public?check=$email"
                val request = Request.Builder()
                    .url(url)
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE

                    if (response.isSuccessful && responseBody != null) {
                        parseAndDisplayResults(responseBody)
                    } else {
                        statusText.text = "Error: Unable to fetch data"
                        statusText.setTextColor(Color.parseColor("#F44336"))
                        Toast.makeText(
                            this@ResultActivity,
                            "Error: ${response.code}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    statusText.text = "Error: ${e.message}"
                    statusText.setTextColor(Color.parseColor("#F44336"))
                    Toast.makeText(
                        this@ResultActivity,
                        "Network Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun parseAndDisplayResults(jsonResponse: String) {
        try {
            val jsonObject = JsonParser.parseString(jsonResponse).asJsonObject

            // Check if request was successful
            val success = jsonObject.get("success")?.asBoolean ?: false

            // Get number of breaches found
            val found = jsonObject.get("found")?.asInt ?: 0

            if (found == 0 || !success) {
                // No breaches found - show simple message like in the image
                statusText.text = "No leaks found"
                statusText.setTextColor(Color.parseColor("#4CAF50"))

                // Don't show any cards, just the status message

            } else {
                // Breaches found
                statusText.text = "⚠ $found Leak(s) Found"
                statusText.setTextColor(Color.parseColor("#F44336"))

                // Get the fields that were leaked
                val fields = mutableListOf<String>()
                if (jsonObject.has("fields")) {
                    val fieldsArray = jsonObject.getAsJsonArray("fields")
                    fieldsArray.forEach { field ->
                        fields.add(field.asString)
                    }
                }

                // Get the sources (websites/dates)
                if (jsonObject.has("sources")) {
                    val sourcesArray = jsonObject.getAsJsonArray("sources")

                    sourcesArray.forEach { sourceElement ->
                        val source = sourceElement.asJsonObject
                        val name = source.get("name")?.asString ?: "Unknown"
                        val date = source.get("date")?.asString ?: "Unknown"

                        val card = createBreachCard(name, date, fields)
                        resultContainer.addView(card)
                    }
                } else {
                    // No sources available
                    statusText.text = "Found $found leaks but no details available"
                    statusText.setTextColor(Color.parseColor("#FF9800"))
                }
            }

        } catch (e: Exception) {
            statusText.text = "Error parsing data"
            statusText.setTextColor(Color.parseColor("#F44336"))
            Toast.makeText(this, "Parse Error: ${e.message}", Toast.LENGTH_LONG).show()

            // Show raw response for debugging
            val debugCard = createInfoCard(
                "Debug Info",
                "Response: $jsonResponse"
            )
            resultContainer.addView(debugCard)
        }
    }

    private fun createBreachCard(site: String, date: String, fields: List<String>): CardView {
        val card = CardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 24)
            }
            radius = 16f
            cardElevation = 8f
            setCardBackgroundColor(Color.parseColor("#FFEBEE"))
        }

        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        // Alert icon
        val alertIcon = TextView(this).apply {
            text = "⚠ Leaked!"
            textSize = 18f
            setTextColor(Color.parseColor("#F44336"))
            typeface = android.graphics.Typeface.DEFAULT_BOLD
        }

        // Site and date
        val siteText = TextView(this).apply {
            text = "$site ($date)"
            textSize = 16f
            setTextColor(Color.parseColor("#212121"))
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setPadding(0, 16, 0, 8)
        }

        // Leaked fields
        if (fields.isNotEmpty()) {
            val fieldsText = TextView(this).apply {
                text = "Leaked data: ${fields.joinToString(", ")}"
                textSize = 14f
                setTextColor(Color.parseColor("#424242"))
                setPadding(0, 8, 0, 0)
            }
            container.addView(alertIcon)
            container.addView(siteText)
            container.addView(fieldsText)
        } else {
            container.addView(alertIcon)
            container.addView(siteText)
        }

        card.addView(container)
        return card
    }

    private fun createInfoCard(title: String, message: String): CardView {
        val card = CardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 24)
            }
            radius = 16f
            cardElevation = 8f
            setCardBackgroundColor(Color.parseColor("#E8F5E9"))
        }

        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val titleText = TextView(this).apply {
            text = title
            textSize = 18f
            setTextColor(Color.parseColor("#4CAF50"))
            typeface = android.graphics.Typeface.DEFAULT_BOLD
        }

        val messageText = TextView(this).apply {
            text = message
            textSize = 16f
            setTextColor(Color.parseColor("#212121"))
            setPadding(0, 16, 0, 0)
        }

        container.addView(titleText)
        container.addView(messageText)
        card.addView(container)

        return card
    }
}