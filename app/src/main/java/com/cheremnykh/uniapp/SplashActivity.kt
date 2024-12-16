package com.cheremnykh.uniapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Показываем индикатор загрузки
        val progressBar: ProgressBar = findViewById(R.id.progress_bar)

        // Имитация короткой задержки для отображения сплэш-экрана
        progressBar.postDelayed({
            handleNavigation()
        }, 2000) // 2 секунды
    }

    private fun handleNavigation() {
        val preferences: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val emailOrPhone = preferences.getString("email_or_phone", null)
        val password = preferences.getString("password", null)
        val autoLogin = preferences.getBoolean("auto_login", false)

        val nextActivity = when {
            emailOrPhone == null || password == null -> {
                // Нет данных о пользователе — отправляем в RegistrationActivity
                RegistrationActivity::class.java
            }
            autoLogin -> {
                // Включён автоматический вход — отправляем в ContentActivity
                ContentActivity::class.java
            }
            else -> {
                // Данные есть, но автоматический вход выключен — отправляем в LoginActivity
                LoginActivity::class.java
            }
        }

        // Запускаем выбранное активити
        startActivity(Intent(this, nextActivity))
        finish()
    }
}
