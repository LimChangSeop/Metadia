package com.example.metadia

import android.bluetooth.BluetoothDevice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.metadia.R.layout.activity_recycler_view_adapter

class RecyclerViewAdapter(devicesArr: ArrayList<BluetoothDevice>) : AppCompatActivity() {

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerViewAdapter : RecyclerViewAdapter
    private var devicesArr = ArrayList<BluetoothDevice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_recycler_view_adapter)

        viewManager = LinearLayoutManager(this)
//        recyclerViewAdapter = RecyclerViewAdapter(devicesArr)
    }
}