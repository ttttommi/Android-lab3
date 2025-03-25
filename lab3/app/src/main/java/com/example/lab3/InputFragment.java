package com.example.lab3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.Intent;

public class InputFragment extends Fragment {

    private Spinner spinnerLanguages; // Випадаючий список для вибору мови

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        // Зв'язуємо елементи інтерфейсу з їх ідентифікаторами
        spinnerLanguages = view.findViewById(R.id.spinner_languages);
        Button buttonOk = view.findViewById(R.id.button_ok);
        Button buttonCancel = view.findViewById(R.id.button_cancel);
        Button buttonOpen = view.findViewById(R.id.button_open);

        // Налаштування адаптера для випадаючого списку
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.programming_languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguages.setAdapter(adapter);

        // Обробка натискання кнопки "OK"
        buttonOk.setOnClickListener(v -> {
            String selectedLanguage = spinnerLanguages.getSelectedItem().toString();
            if (selectedLanguage.equals(getString(R.string.select_language))) {
                // Повідомлення, якщо мову не вибрано
                Toast.makeText(getContext(), getString(R.string.choose_language_message), Toast.LENGTH_SHORT).show();
            } else {
                // Збереження вибраної мови у базу даних
                DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                boolean isInserted = dbHelper.insertLanguage(selectedLanguage);
                if (isInserted) {
                    Toast.makeText(getContext(), "Результат збережено успішно!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Помилка при збереженні.", Toast.LENGTH_SHORT).show();
                }
                ((MainActivity) requireActivity()).showResultFragment(selectedLanguage);
            }
        });

        // Скидання вибору при натисканні кнопки "Cancel"
        buttonCancel.setOnClickListener(v -> spinnerLanguages.setSelection(0));

        // Перехід до активності перегляду даних при натисканні кнопки "Відкрити"
        buttonOpen.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), DataActivity.class));
        });

        return view;
    }
}