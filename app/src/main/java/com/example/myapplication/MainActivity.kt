package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonClick1 = findViewById<Button>(R.id.scheduling)
        buttonClick1.setOnClickListener {
            val intent = Intent(this, Scheduling::class.java)
            startActivity(intent)
        }
        val buttonClickE = findViewById<Button>(R.id.employees)
        buttonClickE.setOnClickListener {
            val intent = Intent(this, Employees::class.java)
            startActivity(intent)
        }
    }
}
