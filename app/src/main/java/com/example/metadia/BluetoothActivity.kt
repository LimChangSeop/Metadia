package com.example.metadia

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ToggleButton
import androidx.core.app.ActivityCompat

class BluetoothActivity : AppCompatActivity() {

    private val REQUEST_ENABLE_BT=1
    private var bluetoothAdapter: BluetoothAdapter?= null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        val onoffBtn: ToggleButton = findViewById(R.id.onoffBtn)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if(bluetoothAdapter != null){
            if(bluetoothAdapter?.isEnabled==false){
                onoffBtn.isChecked = true
            } else {
                onoffBtn.isChecked = false
            }
        }

        onoffBtn.setOnCheckedChangeListener{ _, isChecked ->
            bluetoothOnOff()
        }
    }

    fun bluetoothOnOff() {
        if (bluetoothAdapter == null) {
            Log.d("bluetoothAdapter", "Device doesn't support Bluetooth")
        } else {
            if (bluetoothAdapter?.isEnabled == false) { // 블루투스 꺼져 있으면 활성화
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            } else { // 블루투스 켜져있으면 블루투스 비활성화
                bluetoothAdapter?.disable()
            }
        }
    }
}