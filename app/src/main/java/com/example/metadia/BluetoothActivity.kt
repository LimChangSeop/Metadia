package com.example.metadia

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class BluetoothActivity : AppCompatActivity() {

    private val REQUEST_ENABLE_BT = 1 // 블루투스 요청 코드 0보다 커야함.
    private var bluetoothAdapter: BluetoothAdapter? = null // 블루투스 활성화 위해 사용.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        // onoffBtn으로 토글버튼 선언
        val onoffBtn: ToggleButton = findViewById(R.id.onoffBtn)

        // 활성화 위해 사용. 위에 선언했음.
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter != null) {
            if (bluetoothAdapter?.isEnabled == false) {
                onoffBtn.isChecked = true
            } else {
                onoffBtn.isChecked = false
            }
        }

        // onoffBtn을 눌렀을때 isChecked로 확인 후 활성화, 비활성화
        onoffBtn.setOnCheckedChangeListener { _, isChecked ->
            bluetoothOnOff()
        }
    }

    // 토글버튼을 사용해서 꺼져 있으면 활성화 해주고 켜져 있을 때 터치하면 비활성화 하게 함.

    @SuppressLint("MissingPermission")
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