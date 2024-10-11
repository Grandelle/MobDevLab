package com.cheremnykh.uniapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var byNumberButton: Button
    private lateinit var byEmailButton: Button
    private lateinit var registerButton: Button
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var repeatPasswordInput: EditText
    private var isEmailSelected = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        byNumberButton = findViewById(R.id.by_number)
        byEmailButton = findViewById(R.id.by_email)
        registerButton = findViewById(R.id.register_button)
        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        repeatPasswordInput = findViewById(R.id.repeat_password_input)

        byNumberButton.setOnClickListener { setRegistrationMode(false) }
        byEmailButton.setOnClickListener { setRegistrationMode(true) }
        registerButton.setOnClickListener { register() }
    }

    private fun setRegistrationMode(isEmailMode: Boolean) {
        isEmailSelected = isEmailMode

        if (isEmailMode) {
            byEmailButton.setTextColor(resources.getColor(R.color.selectedColor))
            byNumberButton.setTextColor(resources.getColor(R.color.unselectedColor))
            emailInput.hint = getString(R.string.enter_email)
            emailInput.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        } else {
            byNumberButton.setTextColor(resources.getColor(R.color.selectedColor))
            byEmailButton.setTextColor(resources.getColor(R.color.unselectedColor))
            emailInput.hint = getString(R.string.enter_phone)
            emailInput.inputType = InputType.TYPE_CLASS_PHONE
        }
    }

    private fun register() {
        val emailOrPhone = emailInput.text.toString()
        val password = passwordInput.text.toString()
        val repeatPassword = repeatPasswordInput.text.toString()

        when {
            isEmailSelected && !emailOrPhone.contains("@") -> {
                Toast.makeText(this, "Некорректный email", Toast.LENGTH_SHORT).show()
            }
            !isEmailSelected && !emailOrPhone.startsWith("+") -> {
                Toast.makeText(this, "Некорректный номер телефона", Toast.LENGTH_SHORT).show()
            }
            password.length < 8 -> {
                Toast.makeText(this, "Пароль слишком короткий", Toast.LENGTH_SHORT).show()
            }
            password != repeatPassword -> {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Данные введены корректно. Здесь можно выполнить регистрацию.
                Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
