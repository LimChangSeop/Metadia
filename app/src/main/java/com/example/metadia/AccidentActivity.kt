package com.example.metadia

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_accident.*
import java.nio.file.Files.walk

class AccidentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accident)

        // 누르면 팝업화면이 나오고 실행이 되기 위해 popup 코드를 만듦.
        popup_helmet.setOnClickListener {
            showSettingPopup_helmet()
        }

        popup_drunk.setOnClickListener {
            showSettingPopup_drunk()
        }

        popup_overweight.setOnClickListener {
            showSettingPopup_overweight()
        }

        popup_walk.setOnClickListener {
            showSettingPopup_walk()
        }

        // 이 버튼 누르면 안전법규 화면으로 가짐.
        button1.setOnClickListener {
            val intent = Intent(this, CautionActivity::class.java)
            startActivity(intent)
        }

        // 메인 화면으로 돌아가는 버튼
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    // 옆에 버튼을 터치하면 각각 동영상이 나오게 코드를 만듦.
    private fun showSettingPopup_helmet() {
        var inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater.inflate(R.layout.safety_popup_helmet, null)
        var v:VideoView = view.findViewById(R.id.popup_helmet)
        v.setMediaController(MediaController(this))
        v.setVideoPath("android.resource://com.example.metadia/"+R.raw.helmet)
        // setVideoPath는 "android.resource://com.example.metadia/"+R.raw.(영상 이름)으로 기입해야함. (밑에 코드 다 동일)
        v.start()

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("헬멧 미착용")
            .setNeutralButton("나가기",null)
            .create()

        alertDialog.setView(view)
        alertDialog.show() // show로 보이게 해줌.

    }

    private fun showSettingPopup_drunk() {
        var inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater.inflate(R.layout.safety_popup_drunk, null)
        var v:VideoView = view.findViewById(R.id.popup_drunk)
        v.setMediaController(MediaController(this))
        v.setVideoPath("android.resource://com.example.metadia/"+R.raw.drunk)
        v.start()

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("음주 운전")
            .setNeutralButton("나가기",null)
            .create()

        alertDialog.setView(view)
        alertDialog.show()

    }

    private fun showSettingPopup_overweight() {
        var inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater.inflate(R.layout.safety_popup_overweight, null)
        var v:VideoView = view.findViewById(R.id.popup_overweight)
        v.setMediaController(MediaController(this))
        v.setVideoPath("android.resource://com.example.metadia/"+R.raw.overweight)
        v.start()

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("2인 이상 동승")
            .setNeutralButton("나가기",null)
            .create()

        alertDialog.setView(view)
        alertDialog.show()

    }

    private fun showSettingPopup_walk() {
        var inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater.inflate(R.layout.safety_popup_walk, null)
        var v:VideoView = view.findViewById(R.id.popup_walk)
        v.setMediaController(MediaController(this))
        v.setVideoPath("android.resource://com.example.metadia/"+R.raw.walk)
        v.start()

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("보행자 도로 주행")
            .setNeutralButton("나가기",null)
            .create()

        alertDialog.setView(view)
        alertDialog.show()

    }

}