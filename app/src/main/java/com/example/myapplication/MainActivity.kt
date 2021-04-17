package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn:Button = findViewById(R.id.button)
        btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Toast.makeText(applicationContext, "hello", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MyService::class.java)
        startService(intent)
    }

}