package com.example.kotlinsampleapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        if (Build.VERSION.SDK_INT >= 23) {
            val REQUEST_CODE_CONTACT = 101
            val permissions = arrayOf<String>(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            for (str in permissions) {
                if (checkSelfPermission(str!!) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissions, REQUEST_CODE_CONTACT)
                }
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btActivity: Button = findViewById<View>(R.id.btActivity) as Button
        btActivity.setOnClickListener(listener);

        val btVideo: Button = findViewById<View>(R.id.btVideo) as Button
        btVideo.setOnClickListener(listener);

    }

    val listener = View.OnClickListener { view ->

        when (view.getId()) {
            R.id.btActivity -> {
                val i = Intent(this, TestActivity::class.java)
                startActivity(i)
            }
            R.id.btVideo -> {
                val i = Intent(this, MediaActivity::class.java)
                startActivity(i)
            }
        }
    }
}