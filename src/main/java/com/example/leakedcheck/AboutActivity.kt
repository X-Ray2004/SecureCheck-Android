package com.example.leakedcheck

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val logo3D = findViewById<ImageView>(R.id.logo3D)
        val titleText = findViewById<TextView>(R.id.titleText)
        val teamList = findViewById<TextView>(R.id.teamList)
        val contactButton = findViewById<Button>(R.id.contactButton)

        // Animation
        val anim = AnimationUtils.loadAnimation(this, R.anim.scale_smooth)
        logo3D.startAnimation(anim)
        titleText.startAnimation(anim)
        teamList.startAnimation(anim)

        // Team HTML
        val teamHTML = """
            <b>Team Members:</b><br><br>
            <font color='#1E88E5'><b>Mostafa Ibrahim</b></font><br>
            <a href='mailto:mostafaebrahim24420042@gmail.com'>mostafaebrahim24420042@gmail.com</a><br><br>
            <font color='#1E88E5'><b>Anas Ibrahim</b></font><br>
            <a href='mailto:anas.safwat88@gmail.com'>anas.safwat88@gmail.com</a><br><br>
            <font color='#1E88E5'><b>Rem Khalid</b></font><br>
            <a href='mailto:reemmoslem34@gmail.com'>reemmoslem34@gmail.com</a><br><br>
            <font color='#1E88E5'><b>Fady Ashraf</b></font><br>
            <a href='mailto:fadyashrafesakandr@gmail.com'>fadyashrafesakandr@gmail.com</a><br>
        """.trimIndent()

        teamList.text = Html.fromHtml(teamHTML, Html.FROM_HTML_MODE_LEGACY)
        teamList.movementMethod = LinkMovementMethod.getInstance()

        // Back Button
        contactButton.setOnClickListener {
            finish()
        }
    }
}