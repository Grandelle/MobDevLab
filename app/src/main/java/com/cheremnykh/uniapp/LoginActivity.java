package com.cheremnykh.uniapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private CheckBox autoLoginCheckBox;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Инициализация компонентов
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        autoLoginCheckBox = findViewById(R.id.auto_login_checkbox);
        loginButton = findViewById(R.id.login_button);

        // Обработчик нажатия на кнопку "Войти"
        loginButton.setOnClickListener(v -> {
            String inputEmailOrPhone = emailInput.getText().toString();
            String inputPassword = passwordInput.getText().toString();

            // Чтение данных из SharedPreferences
            SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String savedEmailOrPhone = preferences.getString("email_or_phone", null);
            String savedPassword = preferences.getString("password", null);

            // Проверка введённых данных
            if (savedEmailOrPhone != null && savedEmailOrPhone.equals(inputEmailOrPhone) &&
                    savedPassword != null && savedPassword.equals(inputPassword)) {

                // Сохранение состояния автоматического входа, если выбрано
                preferences.edit()
                        .putBoolean("auto_login", autoLoginCheckBox.isChecked())
                        .apply();

                // Переход к ContentActivity
                Intent intent = new Intent(LoginActivity.this, ContentActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Показать сообщение об ошибке
                Toast.makeText(this, R.string.error_invalid_credentials, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
