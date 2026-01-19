package com.example.app_trying

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import android.net.Uri
import android.widget.VideoView

import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val videoView = findViewById<VideoView>(R.id.videoView)

        val videoUri = Uri.parse(
            "android.resource://" + packageName + "/" + R.raw.shrey
        )

        videoView.setVideoURI(videoUri)

        videoView.setOnPreparedListener { mp ->

            mp.isLooping = true
            mp.setVolume(0f, 0f)

            // Video size (landscape)
            val videoWidth = mp.videoWidth.toFloat()
            val videoHeight = mp.videoHeight.toFloat()

            // Container size (portrait screen)
            val containerWidth = videoView.width.toFloat()
            val containerHeight = videoView.height.toFloat()

            // Scale so that video completely fills the container
            val scaleX = containerWidth / videoWidth
            val scaleY = containerHeight / videoHeight

            val scale = maxOf(scaleX, scaleY)

            videoView.scaleX = scale
            videoView.scaleY = scale
        }



        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)

        btnCalculate.setOnClickListener {

            // 1️⃣ Tomorrow at 00:00 AM
            val startDate = Calendar.getInstance()
            startDate.add(Calendar.DAY_OF_MONTH, 1)
            startDate.set(Calendar.HOUR_OF_DAY, 0)
            startDate.set(Calendar.MINUTE, 0)
            startDate.set(Calendar.SECOND, 0)
            startDate.set(Calendar.MILLISECOND, 0)

            // 2️⃣ Target date at 24:00 PM (i.e. next day 00:00)
            val endDate = Calendar.getInstance()
            endDate.set(
                datePicker.year,
                datePicker.month,
                datePicker.dayOfMonth,
                0, 0, 0
            )
            endDate.set(Calendar.MILLISECOND, 0)
            endDate.add(Calendar.DAY_OF_MONTH, 1) // 24:00 PM

            // 3️⃣ Difference in milliseconds
            val diffMillis = endDate.timeInMillis - startDate.timeInMillis

            // 4️⃣ Convert to days
            val days = diffMillis / (1000 * 60 * 60 * 24)

            // 5️⃣ Add 1 (as per your rule)
            val finalDays = days + 1

            tvResult.text = "Days left: $finalDays"
        }

    }
    override fun onPause() {
        super.onPause()
        findViewById<VideoView>(R.id.videoView)?.pause()
    }

    override fun onResume() {
        super.onResume()
        findViewById<VideoView>(R.id.videoView)?.start()
    }

}
