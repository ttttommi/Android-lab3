package com.example.lab3;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DataActivity extends AppCompatActivity {

    // Змінна для роботи з базою даних
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        // Ініціалізація DatabaseHelper для доступу до бази даних
        dbHelper = new DatabaseHelper(this);

        // Знаходимо елементи інтерфейсу
        TextView textViewData = findViewById(R.id.textView_data); // Відображаємо список даних
        EditText editTextId = findViewById(R.id.editText_id); // Поле введення ID запису
        EditText editTextLanguage = findViewById(R.id.editText_language); // Поле введення нової мови
        Button buttonUpdate = findViewById(R.id.button_update); // Кнопка для оновлення запису
        Button buttonDelete = findViewById(R.id.button_delete); // Кнопка для видалення запису
        Button buttonBack = findViewById(R.id.button_back); // Кнопка для повернення назад

        // Завантажуємо всі дані з бази даних для відображення
        Cursor cursor = dbHelper.getAllLanguages(); // Отримуємо всі записи
        StringBuilder data = new StringBuilder();
        if (cursor.getCount() == 0) {
            // Якщо даних немає, відображаємо повідомлення
            data.append("Сховище пусте");
        } else {
            // Формуємо список записів для відображення
            while (cursor.moveToNext()) {
                data.append("ID: ").append(cursor.getInt(0)) // Додаємо ID запису
                        .append(" - Мова: ").append(cursor.getString(1)) // Додаємо назву мови
                        .append("\n");
            }
        }
        // Встановлюємо текст у текстове поле
        textViewData.setText(data.toString());

        // Обробляємо натискання кнопки "Оновити запис"
        buttonUpdate.setOnClickListener(v -> {
            // Отримуємо значення з полів вводу
            String idStr = editTextId.getText().toString();
            String newLanguage = editTextLanguage.getText().toString();

            // Перевіряємо, що обидва поля заповнені
            if (!idStr.isEmpty() && !newLanguage.isEmpty()) {
                int id = Integer.parseInt(idStr); // Конвертуємо ID з тексту у число
                boolean isUpdated = dbHelper.updateLanguage(id, newLanguage); // Оновлюємо запис у базі даних
                if (isUpdated) {
                    Toast.makeText(this, "Запис оновлено!", Toast.LENGTH_SHORT).show();
                    recreate();
                } else {
                    Toast.makeText(this, "Помилка при оновленні!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Введіть ID та нову мову", Toast.LENGTH_SHORT).show();
            }
        });

        // Обробляємо натискання кнопки "Видалити запис"
        buttonDelete.setOnClickListener(v -> {
            // Отримуємо значення ID з поля вводу
            String idStr = editTextId.getText().toString();

            // Перевіряємо, чи заповнене поле ID
            if (!idStr.isEmpty()) {
                int id = Integer.parseInt(idStr); // Конвертуємо ID з тексту у число
                boolean isDeleted = dbHelper.deleteLanguage(id); // Видаляємо запис з бази даних
                if (isDeleted) {
                    Toast.makeText(this, "Запис видалено!", Toast.LENGTH_SHORT).show();
                    recreate();
                } else {
                    Toast.makeText(this, "Помилка при видаленні!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Введіть ID", Toast.LENGTH_SHORT).show();
            }
        });

        // Обробляємо натискання кнопки "Назад"
        buttonBack.setOnClickListener(v -> {
            finish(); // Повертаємося до попередньої активності
        });
    }
}