package com.cheremnykh.uniapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class RegistrationActivity : AppCompatActivity() {

    private lateinit var byNumberButton: Button
    private lateinit var byEmailButton: Button
    private lateinit var registerButton: Button
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var repeatPasswordInput: EditText
    private var isEmailSelected = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        initViews()
        setupListeners()
    }

    // Инициализация всех View
    private fun initViews() {
        byNumberButton = findViewById(R.id.by_number)
        byEmailButton = findViewById(R.id.by_email)
        registerButton = findViewById(R.id.register_button)
        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        repeatPasswordInput = findViewById(R.id.repeat_password_input)
    }

    // Настройка обработчиков событий для кнопок
    private fun setupListeners() {
        byNumberButton.setOnClickListener { setRegistrationMode(false) }
        byEmailButton.setOnClickListener { setRegistrationMode(true) }
        registerButton.setOnClickListener { validateAndRegister() }
    }

    // Установка режима регистрации (по email или номеру телефона)
    private fun setRegistrationMode(isEmailMode: Boolean) {
        isEmailSelected = isEmailMode

        val selectedColor = ContextCompat.getColor(this, R.color.selectedColor)
        val unselectedColor = ContextCompat.getColor(this, R.color.unselectedColor)

        byEmailButton.setTextColor(if (isEmailMode) selectedColor else unselectedColor)
        byNumberButton.setTextColor(if (isEmailMode) unselectedColor else selectedColor)

        emailInput.apply {
            hint = getString(if (isEmailMode) R.string.enter_email else R.string.enter_phone)
            inputType = if (isEmailMode) InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS else InputType.TYPE_CLASS_PHONE
        }
    }

    // Проверка данных и регистрация
    private fun validateAndRegister() {
        val emailOrPhone = emailInput.text.toString()
        val password = passwordInput.text.toString()
        val repeatPassword = repeatPasswordInput.text.toString()

        when {
            isEmailSelected && !isValidEmail(emailOrPhone) -> showToast(getString(R.string.invalid_email))
            !isEmailSelected && !isValidPhoneNumber(emailOrPhone) -> showToast(getString(R.string.invalid_phone))
            password.length < 8 -> showToast(getString(R.string.short_password))
            password != repeatPassword -> showToast(getString(R.string.passwords_do_not_match))
            else -> saveUserData(emailOrPhone, password)
        }
    }

    // Сохранение данных в SharedPreferences
    private fun saveUserData(emailOrPhone: String, password: String) {
        val preferences: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        preferences.edit().apply {
            putString("email_or_phone", emailOrPhone)
            putString("password", password)
            apply()
        }

        showToast(getString(R.string.registration_success))

        // Переход в ContentActivity
        navigateToContentActivity()
    }

    // Переход к ContentActivity
    private fun navigateToContentActivity() {
        val intent = Intent(this, ContentActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Проверка корректности email
    private fun isValidEmail(email: String): Boolean = email.contains("@")

    // Проверка корректности номера телефона
    private fun isValidPhoneNumber(phone: String): Boolean = phone.startsWith("+")

    // Упрощенный метод отображения сообщений
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
