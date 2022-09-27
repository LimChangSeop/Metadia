package com.example.metadia

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View.inflate
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.metadia.databinding.ActivityCautionBinding.inflate
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Duration
import java.util.jar.Manifest
import javax.xml.datatype.DatatypeConstants.DURATION
import android.Manifest.permission
import android.bluetooth.le.ScanCallback
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 주의 사진 클릭 시 넘어감.
        caution.setOnClickListener {
            val intent = Intent(this, CautionActivity::class.java)
            startActivity(intent)
        }

        inquiry.setOnClickListener {
            val intent = Intent(this, InquiryActivity::class.java)
            startActivity(intent)
        }

        bluetooth.setOnClickListener {
            val intent = Intent(this, BluetoothActivity::class.java)
            startActivity(intent)
        }

    }

}