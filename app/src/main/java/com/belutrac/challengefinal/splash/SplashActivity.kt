package com.belutrac.challengefinal.splash

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.belutrac.challengefinal.R
import com.belutrac.challengefinal.login.LoginActivity
import com.belutrac.challengefinal.login.LoginViewModel
import com.belutrac.challengefinal.main.MainActivity
import com.bumptech.glide.Glide

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DURATION: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val img = findViewById<ImageView>(R.id.img_splash)
        Glide.with(this).asGif().load(R.drawable.pelota_sd).into(img)

        displayAppVersion()

        Handler(Looper.getMainLooper()).postDelayed({

            if (loginViewModel.userLogged())
            {
                startActivity(Intent(this, MainActivity::class.java))
            }else
            {
                startActivity(Intent(this, LoginActivity::class.java))
            }
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
