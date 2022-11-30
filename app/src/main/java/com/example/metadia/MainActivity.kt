package com.example.metadia

import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import android.os.AsyncTask
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    var emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "tmetadia@gmail.com", null))
    // mailto : 보내는 이메일 , tmetadia@gmail.com 이쪽으로 메일을 보내게 됨.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // caution(안전법규 및 사고사례) 클릭 하면 CautionActivity 페이지로 넘어감.
        caution.setOnClickListener {
            val intent = Intent(this, CautionActivity::class.java)
            startActivity(intent)
        }

        // bluetooth(연결) 클릭하면 BluetoothActivity 페이지로 넘어감
        bluetooth.setOnClickListener {
            val intent = Intent(this, BluetoothActivity::class.java)
            startActivity(intent)
        }

        // sendBtn(연결) 클릭하면 이용문의 페이지로 넘어감
        sendBtn.setOnClickListener {
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "")

            startActivity(Intent.createChooser(emailIntent, ""))
        }

        // 주행을 누르면
        mapBtn.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        weatherTask().execute()
        // wetherTask가 없으면 날씨가 전혀 뜨지 않음.
    }
    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<TextView>(R.id.errorText).visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=busan&units=metric&appid=0e02821e627c3fef2196c22528ca9a53").readText(
                    // api.openweathermap 이라는 api를 사용함. 실제로 openWeather에서 제공해주는 api
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            // main화면에 국가 이름, 날짜, 온도 등을 표기해주는 코드 val이 선언이고
            // 밑에 findViewById가 각각의 val을 찾아주고 연결하는 것.
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.KOREAN).format(
                    Date(updatedAt*1000))
                val temp = main.getString("temp")+"°C"
                val weatherDescription = weather.getString("description")

                val address = jsonObj.getString("name")+", "+sys.getString("country")

                /* Populating extracted data into our views */
                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.updated_at).text = updatedAtText
                findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.temp).text = temp

                /* Views populated, Hiding the loader, Showing the main design */
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE

            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }

        }
    }
}

