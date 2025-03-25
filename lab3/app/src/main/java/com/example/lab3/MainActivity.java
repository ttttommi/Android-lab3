package com.example.lab3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Додаємо InputFragment при першому запуску
        if (savedInstanceState == null) {
            // Якщо це перший запуск програми, додаємо InputFragment в контейнер
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new InputFragment())
                    .commit();
        }
    }

    // Метод для відображення ResultFragment з переданим вибраним значенням
    public void showResultFragment(String selectedLanguage) {
        ResultFragment resultFragment = ResultFragment.newInstance(selectedLanguage);

        // Замінюємо поточний фрагмент на ResultFragment і додаємо його в стек повернення
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, resultFragment)
                .addToBackStack(null)  // Додаємо в стек для повернення назад
                .commit();
    }

    // Метод для повернення до InputFragment
    public void showInputFragment() {
        // Відкриваємо попередній фрагмент зі стеку
        getSupportFragmentManager().popBackStack();
    }
}