package com.example.metadia

import androidx.databinding.DataBindingUtil.setContentView
import java.text.SimpleDateFormat
import java.util.*
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.widget.Toast
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import com.example.metadia.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "aaa@naver.com", null))

    @SuppressLint("SetTextI18n", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.Q)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 주의 사진 클릭 시 넘어감.
        caution.setOnClickListener {
            val intent = Intent(this, CautionActivity::class.java)
            startActivity(intent)
        }

        sendBtn.setOnClickListener {
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "")

            startActivity(Intent.createChooser(emailIntent, ""))
        }

        bluetooth.setOnClickListener {
            val intent = Intent(this, BluetoothActivity::class.java)
            startActivity(intent)
        }

        rideBtn.setOnClickListener {
            val intent = Intent(this, RideActivity::class.java)
            startActivity(intent)
        }

        weather.setOnClickListener {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
        }

    }

}

