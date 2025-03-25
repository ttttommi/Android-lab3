package com.example.lab3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {

    private static final String ARG_LANGUAGE = "language";

    // Статичний метод для створення екземпляру ResultFragment з переданим параметром
    public static ResultFragment newInstance(String language) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LANGUAGE, language);  // Передаємо вибрану мову в Bundle
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Створюємо вигляд фрагмента з layout
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        // Ініціалізація елементів інтерфейсу
        TextView textViewResult = view.findViewById(R.id.textView_result);
        Button buttonCancel = view.findViewById(R.id.button_cancel);

        // Отримуємо передану мову та відображаємо її в TextView
        if (getArguments() != null) {
            String language = getArguments().getString(ARG_LANGUAGE);
            textViewResult.setText(language);
        }

        // Обробник натискання кнопки Cancel
        buttonCancel.setOnClickListener(v -> {
            // Повертаємось до InputFragment
            ((MainActivity) requireActivity()).showInputFragment();
        });

        return view;  // Повертаємо вигляд фрагмента
    }
}