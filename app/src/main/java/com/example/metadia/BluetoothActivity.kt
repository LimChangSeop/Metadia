package com.example.metadia

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.ParcelUuid
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.metadia.databinding.ActivityMainBinding

val ScanFilterService_UUID : ParcelUuid = ParcelUuid.fromString("00001101-0000-1000-8000-00805f9b34fb")

class BluetoothActivity : AppCompatActivity() {

    private val REQUEST_ENABLE_BT = 1
    private var bluetoothAdapter: BluetoothAdapter? = null
    private val REQUEST_PERMISSIONS=2
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerViewAdapter : RecyclerViewAdapter

//  scan중인지 나타내는 state 변수
    private var scanning: Boolean = false
//  scan한 Device를 담는 배열
    private var devicesArr = ArrayList<BluetoothDevice>()
    private val SCAN_PERIOD = 1000
    private val handler = Handler()
    private val mLeScanCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)

    object:ScanCallback() {
        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.d("scanCallback", "BLE Scan Failed : " + errorCode)
        }
        @SuppressLint("MissingPermission")
        override fun onBatchScanResults(results: MutableList<ScanResult > ?) {
            super.onBatchScanResults(results)
            results?.let {
                // results is not null
                for(result in it) {
                    if(!devicesArr.contains(result.device) && result.device.name!=null) devicesArr.add(result.device)
                }
            }
        }
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            result?.let {
                // result is not null
                if(!devicesArr.contains(it.device) && it.device.name!=null) devicesArr.add(it.device)
                val recyclerViewAdapter = null
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun buildScanFilters():List<ScanFilter>{
        val ScanFilter = ScanFilter.Builder()
            .setServiceUuid(ScanFilterService_UUID)
            .build()
        Log.d(TAG, "buildScanFilters")
        return listOf(ScanFilter)
    }

    private fun buildSacnSettings() = ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_POWER).build()
    @SuppressLint("MissingPermission")
    private fun scanDeivce(state:Boolean) = if(state){
        bluetoothAdapter?.bluetoothLeScanner?.startScan(buildScanFilters(),
        buildSacnSettings(), mLeScanCallback)
    } else {

    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private  fun scanDevice(state:Boolean) = if(state) {
        handler.postDelayed({
            scanning = false
            bluetoothAdapter?.bluetoothLeScanner?.stopScan(mLeScanCallback)
            }, SCAN_PERIOD.toLong())
            scanning = true
            devicesArr.clear()
            bluetoothAdapter?.bluetoothLeScanner?.startScan(mLeScanCallback)
    } else {
        scanning = false
        bluetoothAdapter?.bluetoothLeScanner?.stopScan(mLeScanCallback)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissions granted!", Toast.LENGTH_SHORT).show()
                } else {
                    requestPermissions(permissions, REQUEST_PERMISSIONS)
                    Toast.makeText(this, "Permissions must be granted", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingInflatedId", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        val onoffBtn: ToggleButton = findViewById(R.id.onoffBtn)
        val scanBtn: Button = findViewById(R.id.scanBtn)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        viewManager = LinearLayoutManager(this)
        recyclerViewAdapter = RecyclerViewAdapter(devicesArr)
        val recyclerView = findViewById<RecyclerView > (R.id.recyclerView).apply {
            layoutManager = viewManager
            adapter = recyclerViewAdapter
        }

        if (bluetoothAdapter != null) {
            if (bluetoothAdapter?.isEnabled == false) {
                onoffBtn.isChecked = true
            } else {
                onoffBtn.isChecked = false
            }
        }

        onoffBtn.setOnCheckedChangeListener { _, isChecked ->
            bluetoothOnOff()
        }

        if (bluetoothAdapter != null) {
            if (bluetoothAdapter?.isEnabled == false) {
                onoffBtn.isChecked = true
                scanBtn.isVisible = false
            } else {
                onoffBtn.isChecked = false
                scanBtn.isVisible = true
            }
        }
        onoffBtn.setOnCheckedChangeListener {
            _,
            isChecked ->
            bluetoothOnOff()
            scanBtn.visibility = if (scanBtn.visibility == View.VISIBLE) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        }

        scanBtn.setOnClickListener {
            v:View -> // Scan Button Onclick
            if(!hasPermissions(this, PERMISSIONS)){
                requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS)
            }
            scanDevice(true)
        }
    }

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

    class RecyclerViewAdapter(private val myDataset: ArrayList<BluetoothDevice>) :
        RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

        class MyViewHolder(val linearView: LinearLayout) : RecyclerView.ViewHolder(linearView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            TODO("Not yet implemented")
            val linearView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false) as LinearLayout
            return MyViewHolder(linearView)
        }

        @SuppressLint("MissingPermission")
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            TODO("Not yet implemented")
            val itemName:TextView = holder.linearView.findViewById(R.id.item_name)
            val itemAddress:TextView = holder.linearView.findViewById(R.id.item_address)
            itemName.text = myDataset[position].name
            itemAddress.text = myDataset[position].address
        }

        override fun getItemCount() = myDataset.size
        }
    private fun Handler.postDelayed(function: () -> Unit?, scanPeriod: Int){

    }

    private fun hasPermissions(context: Context?, permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED
                )
                    return false
            }
        }
        return true
    }
}

private fun Nothing?.notifyDataSetChanged() {
    TODO("Not yet implemented")
    Toast.makeText(this,"error", Toast.LENGTH_SHORT).show()
}
