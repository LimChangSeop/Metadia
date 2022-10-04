package com.example.metadia

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.metadia.databinding.ActivityInquiryBinding
import kotlinx.android.synthetic.main.activity_inquiry.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class InquiryActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {

        var emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "aaa@naver.com", null))

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inquiry)

        sendBtn.setOnClickListener {
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "")

            startActivity(Intent.createChooser(emailIntent, ""))
        }
    }
}