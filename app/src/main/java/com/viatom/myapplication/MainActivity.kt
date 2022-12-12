package com.viatom.myapplication

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.webkit.*
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.viatom.myapplication.R

class MainActivity : AppCompatActivity() {
    lateinit var jumpButton:AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jumpButton=findViewById(R.id.jump_web)
        jumpButton.setOnClickListener {
            startActivity(Intent(this,WebActivity::class.java))
        }

    }



}