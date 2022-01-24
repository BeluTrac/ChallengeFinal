package com.belutrac.challengefinal.splash

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.belutrac.challengefinal.R
import com.belutrac.challengefinal.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DURATION: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        displayAppVersion()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        },SPLASH_DURATION)
    }

    private fun displayAppVersion() {
        try {
            val version = this.packageManager.getPackageInfo(this.packageName, 0).versionName
            findViewById<TextView>(R.id.tv_versioname).text = version
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }
    }
