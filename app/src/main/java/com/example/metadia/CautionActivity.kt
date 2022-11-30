package com.example.metadia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_caution.*

class CautionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caution)

        // 우측 화살표 버튼 누르면 메인 화면으로 돌아감.
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 사고사례로 가는 버튼
        button2.setOnClickListener {
            val intent = Intent(this, AccidentActivity::class.java)
            startActivity(intent)
        }
    }
}